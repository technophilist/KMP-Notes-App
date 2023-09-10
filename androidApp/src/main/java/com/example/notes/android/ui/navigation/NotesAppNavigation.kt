package com.example.notes.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes.android.ui.home.AndroidHomeViewModel
import com.example.notes.android.ui.home.HomeScreen
import com.example.notes.android.ui.notedetail.AndroidNoteDetailViewModel
import com.example.notes.android.ui.notedetail.NoteDetailScreen
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
                onSearchQueryChange = viewModel::search,
                onNoteItemClick = {
                    val route = NavigationDestinations.NoteDetailScreen.buildRoute(it.id)
                    navController.navigate(route)
                },
                onCreateNewNoteButtonClick = {
                    val route = NavigationDestinations.NoteDetailScreen.buildRoute()
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = NavigationDestinations.NoteDetailScreen.route,
            arguments = listOf(
                navArgument(name = NavigationDestinations.NoteDetailScreen.NAV_ARG_NOTE_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            val viewModel = viewModel {
                AndroidNoteDetailViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    notesRepository = appModule.provideNotesRepository()
                )
            }
            val noteTitle by viewModel.titleTextStream.collectAsStateWithLifecycle()
            val noteContent by viewModel.contentTextStream.collectAsStateWithLifecycle()
            NoteDetailScreen(
                noteTitle = noteTitle,
                noteContent = noteContent,
                onNoteTitleChange = viewModel::onTitleChange,
                onNoteContentChange = viewModel::onContentChange,
                onBackButtonClick = navController::popBackStack
            )
        }
    }
}