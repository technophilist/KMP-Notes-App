package com.example.notes.android.ui.navigation

sealed class NavigationDestinations(val route: String) {
    object HomeScreen : NavigationDestinations(route = "notes_app_home_screen")
}