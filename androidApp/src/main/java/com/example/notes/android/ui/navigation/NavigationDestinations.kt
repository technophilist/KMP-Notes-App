package com.example.notes.android.ui.navigation

sealed class NavigationDestinations(val route: String) {
    object HomeScreen : NavigationDestinations(route = "notes_app_home_screen")
    object NoteDetailScreen :
        NavigationDestinations(route = "notes_app_note_detail_screen/{noteId}") {
        const val NAV_ARG_NOTE_ID = "noteId"
        fun buildRoute(noteId: Long): String {
            return "notes_app_note_detail_screen/$noteId"
        }
    }
    
}