package com.example.notes.android.ui.home

import android.net.wifi.hotspot2.pps.HomeSp
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.notes.android.ui.components.NoteListCard
import com.example.notes.domain.Note
import com.example.notes.ui.home.HomeScreenUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    onSearchQueryChange: (String) -> Unit,
    onNoteItemClick: (Note) -> Unit,
) {
    HomeScreen(
        modifier = modifier,
        savedNotes = uiState.savedNotes,
        notesForSearchQuery = uiState.searchResults,
        onSearchQueryChange = onSearchQueryChange,
        onNoteItemClick = onNoteItemClick
    )
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    savedNotes: List<Note>,
    notesForSearchQuery: List<Note>,
    onSearchQueryChange: (String) -> Unit,
    onNoteItemClick: (Note) -> Unit
) {
    var currentSearchQuery by remember { mutableStateOf("") }
    var isSearchBarActive by remember { mutableStateOf(false) }
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
                    suggestionsForQuery = notesForSearchQuery
                )
            }
            if (!isSearchBarActive) {
                noteItems(notes = savedNotes, onClick = { onNoteItemClick(it) })
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(16.dp),
            onClick = { /*TODO*/ },
            content = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) }
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
                noteItems(notes = suggestionsForQuery, onClick = {/*TODO*/ })
            }
        }
    }
}

@ExperimentalMaterial3Api
private fun LazyListScope.noteItems(notes: List<Note>, onClick: (Note) -> Unit) {
    items(notes) {
        NoteListCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            savedNote = it,
            onClick = { onClick(it) }
        )
    }
}