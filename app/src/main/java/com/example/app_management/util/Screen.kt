package com.example.app_management.util

sealed class Screen(val route: String) {
    data object HomeScreen: Screen("home_screen")
    data object DetailAppScreen: Screen("detail_app_screen")
}
