package com.example.notes.android.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.notes.android.ui.components.SwipeableNoteListCard
import com.example.notes.domain.Note
import com.example.notes.ui.home.HomeScreenUiState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    onSearchQueryChange: (String) -> Unit,
    onNoteItemClick: (Note) -> Unit,
    onCreateNewNoteButtonClick: () -> Unit,
    onNoteDismissed: (Note) -> Unit,
    onUndoDeleteButtonClick: () -> Unit
) {
    HomeScreen(
        modifier = modifier,
        savedNotes = uiState.savedNotes,
        notesForSearchQuery = uiState.searchResults,
        onSearchQueryChange = onSearchQueryChange,
        onNoteItemClick = onNoteItemClick,
        onCreateNewNoteButtonClick = onCreateNewNoteButtonClick,
        onNoteDismissed = onNoteDismissed,
        onUndoDeleteButtonClick = onUndoDeleteButtonClick
    )
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    savedNotes: List<Note>,
    notesForSearchQuery: List<Note>,
    onSearchQueryChange: (String) -> Unit,
    onNoteItemClick: (Note) -> Unit,
    onCreateNewNoteButtonClick: () -> Unit,
    onNoteDismissed: (Note) -> Unit,
    onUndoDeleteButtonClick: () -> Unit
) {
    var currentSearchQuery by remember { mutableStateOf("") }
    var isSearchBarActive by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val onNoteDismissedCallback = { dismissedNote: Note ->
        onNoteDismissed(dismissedNote)
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val snackbarResult =
                snackbarHostState.showSnackbar(message = "Note deleted", actionLabel = "Undo")
            if (snackbarResult == SnackbarResult.ActionPerformed) onUndoDeleteButtonClick()
        }
    }
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        ) {
            item {
                AnimatedSearchBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    query = currentSearchQuery,
                    isSearchBarActive = isSearchBarActive,
                    onQueryChange = {
                        currentSearchQuery = it
                        onSearchQueryChange(it)
                    },
                    onBackButtonClick = { isSearchBarActive = false },
                    onActiveChange = { isSearchBarActive = it },
                    onClearSearchQueryButtonClick = {
                        currentSearchQuery = ""
                        onSearchQueryChange("")
                    },
                    suggestionsForQuery = notesForSearchQuery,
                    onNoteDismissed = { onNoteDismissedCallback(it) },
                    onNoteItemClick = onNoteItemClick
                )
            }
            if (!isSearchBarActive) {
                noteItems(
                    notes = savedNotes,
                    onClick = { onNoteItemClick(it) },
                    onDismissed = { onNoteDismissedCallback(it) }
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(16.dp),
            onClick = onCreateNewNoteButtonClick,
            content = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) }
        )
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun AnimatedSearchBar(
    query: String,
    isSearchBarActive: Boolean,
    onQueryChange: (String) -> Unit,
    onClearSearchQueryButtonClick: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onBackButtonClick: () -> Unit,
    onNoteDismissed: (Note) -> Unit,
    onNoteItemClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
    suggestionsForQuery: List<Note>
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val leadingIcon = @Composable {
        AnimatedContent(targetState = isSearchBarActive, label = "") { isActive ->
            if (isActive) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            } else {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
        }
    }

    val trailingIcon = @Composable {
        AnimatedContent(targetState = isSearchBarActive, label = "") { isActive ->
            if (isActive) {
                IconButton(
                    onClick = onClearSearchQueryButtonClick,
                    content = { Icon(imageVector = Icons.Filled.Close, contentDescription = null) }
                )
            }
        }
    }
    Column(modifier = modifier) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .sizeIn(
                    maxWidth = screenWidth, // need sizeIn modifier to prevent exception being thrown about size being set to infinity
                    maxHeight = screenHeight
                ),
            query = query,
            onQueryChange = onQueryChange,
            onSearch = {
                // no need for this callback because this app uses instant search
            },
            active = isSearchBarActive,
            onActiveChange = onActiveChange,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            placeholder = { Text(text = "Search notes") },
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                noteItems(
                    notes = suggestionsForQuery,
                    onClick = onNoteItemClick,
                    onDismissed = onNoteDismissed
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
private fun LazyListScope.noteItems(
    notes: List<Note>,
    onClick: (Note) -> Unit,
    onDismissed: (Note) -> Unit
) {
    items(notes) {
        SwipeableNoteListCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            savedNote = it,
            onClick = { onClick(it) },
            onDismissed = { onDismissed(it) }
        )
    }
}