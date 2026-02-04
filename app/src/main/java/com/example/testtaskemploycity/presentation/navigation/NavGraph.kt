package com.example.testtaskemploycity.presentation.navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testtaskemploycity.presentation.common.CustomBottomNavigation
import com.example.testtaskemploycity.presentation.favorites.FavoritesScreen
import com.example.testtaskemploycity.presentation.filters.FilterScreen
import com.example.testtaskemploycity.presentation.rates.RatesScreen
import com.example.testtaskemploycity.presentation.rates.viewmodel.RatesEvent
import com.example.testtaskemploycity.presentation.rates.viewmodel.RatesViewModel
import com.example.testtaskemploycity.presentation.ui.theme.AppColors


@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val selectedTab = when (currentDestination?.route) {
        Screen.Rates.route -> 0
        Screen.Favorites.route -> 1
        else -> 0
    }

    val showBottomBar =
        currentDestination?.route in listOf(Screen.Rates.route, Screen.Favorites.route)

    Scaffold(
        containerColor = AppColors.color_bg_default,
        bottomBar = {
            if (showBottomBar) {
                CustomBottomNavigation(
                    selectedTab = selectedTab,
                    onTabSelected = { tabIndex ->
                        val route = when (tabIndex) {
                            0 -> Screen.Rates.route
                            1 -> Screen.Favorites.route
                            else -> Screen.Rates.route
                        }
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Rates.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Rates.route) { _ ->
                val viewModel: RatesViewModel = hiltViewModel()
                RatesScreen(
                    viewModel = viewModel,
                    onNavigateToFilter = {
                        navController.navigate("filter")

                    }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen()
            }
            composable("filter") { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Screen.Rates.route)
                }
                val viewModel: RatesViewModel = hiltViewModel(parentEntry)
                val state by viewModel.state.collectAsState()

                FilterScreen(
                    currentSortType = state.sortType,
                    onSortTypeSelected = { sortType ->
                        viewModel.onEvent(RatesEvent.ChangeSortType(sortType))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
