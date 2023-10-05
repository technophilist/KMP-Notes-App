package com.example.notes.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notes.domain.Note

/**
 * A composable that displays the information of a [Note], in a card. Some of the contents
 * might get truncated if the content of the [Note] is too long. Mainly meant to be used in
 * a list, such as a [LazyColumn] or a [LazyRow].
 *
 * @param modifier The modifier to be applied to the card.
 * @param savedNote The note to be displayed.
 * @param onClick The click listener for the card.
 */
@ExperimentalMaterial3Api
@Composable
fun NoteListCard(
    modifier: Modifier = Modifier,
    savedNote: Note,
    onClick: () -> Unit
) {
    OutlinedCard(modifier = modifier, onClick = onClick) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = savedNote.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 3
            )
            Text(
                text = savedNote.content,
                maxLines = 3,
                minLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SwipeableNoteListCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDismissed: () -> Unit,
    savedNote: Note,
) {
    val dismissState = remember(savedNote) {
        DismissState(
            initialValue = DismissValue.Default,
            confirmValueChange = {
                if (it != DismissValue.DismissedToStart) return@DismissState false
                onDismissed()
                true
            }
        )
    }

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {},
        dismissContent = {
            NoteListCard(
                modifier = Modifier.fillMaxWidth(),
                savedNote = savedNote,
                onClick = onClick
            )
        },
        directions = setOf(DismissDirection.EndToStart)
    )
}