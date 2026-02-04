package com.example.testtaskemploycity.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object Rates : Screen(
        route = "rates",
    )
    object Favorites : Screen(
        route = "favorites",
    )
}
