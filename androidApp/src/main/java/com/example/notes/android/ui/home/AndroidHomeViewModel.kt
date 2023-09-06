package com.example.notes.android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.NotesRepository
import com.example.notes.ui.home.HomeViewModel

class AndroidHomeViewModel(notesRepository: NotesRepository) : ViewModel() {

    private val viewModel = HomeViewModel(
        notesRepository = notesRepository,
        viewModelScope = viewModelScope
    )

    val uiState = viewModel.uiState
    fun search(searchText: String) = viewModel.search(searchText)
}