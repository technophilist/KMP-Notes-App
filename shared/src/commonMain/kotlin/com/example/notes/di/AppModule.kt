package com.example.notes.di

import com.example.notes.data.NotesRepository

expect class AppModule {
    /**
     * Provides an implementation of [NotesRepository]
     */
    fun provideNotesRepository(): NotesRepository
}