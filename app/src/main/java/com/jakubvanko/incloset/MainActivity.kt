package com.jakubvanko.incloset

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jakubvanko.incloset.ui.theme.InclosetTheme
import androidx.navigation.compose.rememberNavController
import com.jakubvanko.incloset.ui.screens.ManageScreen
import com.jakubvanko.incloset.ui.screens.OverviewScreen
import com.jakubvanko.incloset.ui.screens.ProfileScreen
import com.jakubvanko.incloset.ui.screens.SettingsScreen

class MainActivity : ComponentActivity() {
    private var user: FirebaseUser? = null
    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
            this.onSignInFinished(res)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            triggerSignInScreen()
        } else {
            setContent {
                MainView()
            }
        }
    }

    private fun triggerSignInScreen() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInFinished(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            user = FirebaseAuth.getInstance().currentUser
        } else {
            Log.e("MainActivity.kt", "Error logging in " + response?.error?.errorCode)
        }
    }
}

enum class Routes() {
    Overview,
    Manage,
    Settings,
    Profile
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainView() {
    val navController = rememberNavController();

    // var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        Pair(Routes.Overview, Icons.Rounded.Home),
        Pair(Routes.Manage, Icons.Rounded.Edit),
        Pair(Routes.Settings, Icons.Rounded.Settings),
        Pair(Routes.Profile, Icons.Rounded.AccountCircle)
    )

    InclosetTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.second, contentDescription = item.first.name) },
                            label = { Text(item.first.name) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.first.name } == true,
                            onClick = {
                                navController.navigate(item.first.name) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Overview.name
                ) {
                    composable(Routes.Overview.name) { OverviewScreen() }
                    composable(Routes.Manage.name) { ManageScreen() }
                    composable(Routes.Settings.name) { SettingsScreen() }
                    composable(Routes.Profile.name) { ProfileScreen() }
                }
            }
        }
    }
}