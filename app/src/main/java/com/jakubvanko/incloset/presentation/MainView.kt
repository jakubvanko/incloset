package com.jakubvanko.incloset.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jakubvanko.incloset.presentation.overview.OverviewScreen
import com.jakubvanko.incloset.presentation.settings.SettingsScreen
import com.jakubvanko.incloset.presentation.theme.InclosetTheme
import com.jakubvanko.incloset.ui.screens.ProfileScreen

enum class Routes {
    Overview,
    Manage,
    Settings,
    Profile
}

@Composable
fun NavRoutes(
    navController: NavHostController,
    paddingValues: PaddingValues,
    clothingViewModel: ClothingViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Overview.name,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Routes.Overview.name) { OverviewScreen(clothingViewModel) }
        composable(Routes.Settings.name) { SettingsScreen(clothingViewModel) }
        composable(Routes.Profile.name) { ProfileScreen() }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(clothingViewModel: ClothingViewModel) {
    val navController = rememberNavController();

    val items = listOf(
        Pair(Routes.Overview, Icons.Rounded.Home),
        Pair(Routes.Settings, Icons.Rounded.Settings),
        //Pair(Routes.Manage, Icons.Rounded.Edit),
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
                NavRoutes(navController, it, clothingViewModel)
            }
        }
    }
}