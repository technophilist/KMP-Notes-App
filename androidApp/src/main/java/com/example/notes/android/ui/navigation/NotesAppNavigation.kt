package com.example.notes.android.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.android.ui.home.AndroidHomeViewModel
import com.example.notes.android.ui.home.HomeScreen
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
                onCreateNewNoteButtonClick = {/*TODO*/ }
            )
        }

        composable(route = NavigationDestinations.NoteDetailScreen.route) {
            var noteTitle by remember { mutableStateOf("") } // TODO
            var noteContent by remember { mutableStateOf("") } // TODO
            NoteDetailScreen(
                noteTitle = noteTitle,
                noteContent = noteContent,
                onNoteTitleChange = { noteTitle = it },
                onNoteContentChange = { noteContent = it },
                onBackButtonClick = { navController.popBackStack() }
            )
        }
    }
}