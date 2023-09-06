package com.example.notes.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.android.ui.home.AndroidHomeViewModel
import com.example.notes.android.ui.home.HomeScreen
import com.example.notes.di.AppModule

@Composable
fun NotesAppNavigation(
    appModule: AppModule,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.HomeScreen.route
    ) {
        composable(route = NavigationDestinations.HomeScreen.route) {
            val viewModel = viewModel {
                AndroidHomeViewModel(notesRepository = appModule.provideNotesRepository())
            }
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(
                uiState = uiState,
                onSearchQueryChange = viewModel::search
            )
        }
    }
}