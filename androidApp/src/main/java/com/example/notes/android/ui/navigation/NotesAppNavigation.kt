package com.example.notes.android.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
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
            var noteTitle by rememberSaveable { mutableStateOf("") } // TODO
            var noteContent by rememberSaveable { mutableStateOf("") } // TODO
            val viewModel = viewModel {
                AndroidNoteDetailViewModel(
                    notesRepository = appModule.provideNotesRepository(),
                    savedStateHandle = this.createSavedStateHandle()
                )
            }

            NoteDetailScreen(
                noteTitle = noteTitle,
                noteContent = noteContent,
                onNoteTitleChange = {
                    noteTitle = it
                    viewModel.onTitleChange(it)
                },
                onNoteContentChange = {
                    noteContent = it
                    viewModel.onContentChange(it)
                },
                onBackButtonClick = { navController.popBackStack() }
            )
        }
    }
}