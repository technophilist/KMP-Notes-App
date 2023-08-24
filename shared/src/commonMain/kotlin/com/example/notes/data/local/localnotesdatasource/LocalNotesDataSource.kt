package com.example.notes.data.local.localnotesdatasource

import com.example.notes.database.SavedNoteEntity

import kotlinx.coroutines.flow.Flow

/**
 * Interface that handles the DAO operations for [SavedNoteEntity].
 */
interface LocalNotesDataSource {
    /**
     * A [Flow] of all saved notes.
     */
    val savedNotesStream: Flow<List<SavedNoteEntity>>

    /**
     * Used to save a note.
     */
    suspend fun saveNote(noteEntity: SavedNoteEntity)

    /**
     * Used to permanently delete a note.
     */
    suspend fun permanentlyDeleteNoteWithId(id: Long)

    /**
     * Used to mark a note, with the specified [id], as deleted.
     */
    suspend fun markNoteAsDeleted(id: Long)

    /**
     * Used to mark a note, with the specified [id], as not deleted.
     */
    suspend fun markNoteAsNotDeleted(id: Long)

    /**
     * Used to delete all saved notes.
     */
    suspend fun deleteAllNotes()
}
