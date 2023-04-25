package com.jakubvanko.incloset.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import com.jakubvanko.incloset.data.repository.SettingsAction
import com.jakubvanko.incloset.presentation.overview.OverviewScreen
import com.jakubvanko.incloset.presentation.settings.SettingsScreen
import com.jakubvanko.incloset.presentation.theme.InclosetTheme
import com.jakubvanko.incloset.ui.screens.ProfileScreen

enum class Routes {
    Overview,
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
        composable(Routes.Profile.name) { ProfileScreen(clothingViewModel) }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(clothingViewModel: ClothingViewModel) {
    val navController = rememberNavController();
    val regex = Regex("([a-z])([A-Z]+)")

    val items = listOf(
        Pair(Routes.Overview, Icons.Rounded.Home),
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
                                clothingViewModel.isTopBarVisible = false
                                clothingViewModel.isFABVisible = item.first == Routes.Overview
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
            },
                floatingActionButton = {
                    if (clothingViewModel.isFABVisible) {
                        ExposedDropdownMenuBox(
                            expanded = clothingViewModel.isFABExpanded,
                            onExpandedChange = {
                                clothingViewModel.isFABExpanded = !clothingViewModel.isFABExpanded
                            }) {
                            ExtendedFloatingActionButton(
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(0.35F),
                                text = { Text(text = "Edit") },
                                icon = { Icon(Icons.Filled.Edit, "Edit") },
                                onClick = { /*TODO*/ })
                            ExposedDropdownMenu(
                                expanded = clothingViewModel.isFABExpanded,
                                onDismissRequest = { clothingViewModel.isFABExpanded = false }) {
                                SettingsAction.values().sortedBy { it.name }.forEach {
                                    DropdownMenuItem(
                                        text = { Text(text = it.name.replace(regex, "$1 $2")) },
                                        onClick = {
                                            clothingViewModel.isFABExpanded = false
                                            clothingViewModel.isFABVisible = false
                                            clothingViewModel.isTopBarVisible = true
                                            clothingViewModel.currentSettingsAction = it
                                            navController.navigate(Routes.Settings.name) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    )
                                }
                            }
                        }
                    }
                },
                topBar = {
                    if (clothingViewModel.isTopBarVisible) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = clothingViewModel.currentSettingsAction.name.replace(
                                        regex,
                                        "$1 $2"
                                    )
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    clothingViewModel.isTopBarVisible = false
                                    clothingViewModel.isFABVisible = true
                                    navController.navigate(Routes.Overview.name) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowLeft,
                                        contentDescription = "Go back"
                                    )
                                }
                            },
                        )
                    }
                }
            ) {
                NavRoutes(navController, it, clothingViewModel)
            }
        }
    }
}