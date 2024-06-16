package com.example.mockapiapplication.nav

sealed class Screen(val route: String) {
    data object Welcome : Screen(route = "welcome_Screen")
    data object Home : Screen(route = "home_Screen")
}