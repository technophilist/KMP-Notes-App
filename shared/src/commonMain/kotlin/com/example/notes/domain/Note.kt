package com.example.notes.domain

import com.example.notes.database.SavedNoteEntity


data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val isDeleted:Boolean
)

fun SavedNoteEntity.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        isDeleted = this.isDeleted == 1L
    )
}

