package com.example.notes.ui.home

import com.example.notes.data.NotesRepository
import com.example.notes.domain.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val notesRepository: NotesRepository,
    private val viewModelScope: CoroutineScope
) {

    /**
     * The current [HomeScreenUiState]
     */
    private val _uiState = MutableStateFlow(HomeScreenUiState(isLoadingSavedNotes = true))
    val uiState = _uiState as StateFlow<HomeScreenUiState>

    init {
        notesRepository.savedNotesStream.onEach { savedNotesList ->
            _uiState.update { it.copy(isLoadingSavedNotes = false, savedNotes = savedNotesList) }
        }.launchIn(viewModelScope)
    }

    /**
     * Searches for notes with titles or contents containing the given [searchText].
     */
    fun search(searchText: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingSearchResults = true) }
            val savedNotes = _uiState.value.savedNotes
            // filtering notes with titles containing the search text
            val notesWithTitleContainingSearchText = savedNotes.filter {
                it.title.contains(searchText, ignoreCase = true)
            }
            _uiState.update {
                it.copy(searchResults = notesWithTitleContainingSearchText)
            }

            // filtering notes with the content containing the search text
            // since this is slower than the previous filtering operation above,
            // update ui state independently
            val notesWithContentContainingSearchText = savedNotes.filter {
                it.content.contains(searchText, ignoreCase = true)
            }
            _uiState.update {
                it.copy(searchResults = (it.searchResults + notesWithContentContainingSearchText).distinct())
            }
            _uiState.update {
                it.copy(isLoadingSearchResults = false)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { notesRepository.deleteNote(note) }
    }

}


/**
 * The UI state for the home screen.
 */
data class HomeScreenUiState(
    val isLoadingSavedNotes: Boolean = false,
    val isLoadingSearchResults: Boolean = false,
    val savedNotes: List<Note> = emptyList(),
    val searchResults: List<Note> = emptyList()
)
