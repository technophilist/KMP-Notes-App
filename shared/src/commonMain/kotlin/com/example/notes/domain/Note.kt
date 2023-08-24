package com.example.notes.domain

import com.example.notes.database.SavedNoteEntity


/**
 * Data class representing a note.
 *
 * @property id The ID of the note.
 * @property title The title of the note.
 * @property content The content of the note.
 * @property isDeleted Whether the note is deleted or not.
 */
data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val isDeleted: Boolean
)

/**
 * Used to convert a [SavedNoteEntity] to a [Note].
 */
fun SavedNoteEntity.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        isDeleted = this.isDeleted == 1L
    )
}

/**
 * Used to convert a [Note] to a [SavedNoteEntity].
 */
fun Note.toSavedNoteEntity(): SavedNoteEntity {
    return SavedNoteEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        isDeleted = if (this.isDeleted) 1 else 0
    )
}

