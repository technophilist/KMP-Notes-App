package com.example.notes.android

import android.app.Application
import androidx.work.Configuration
import com.example.notes.android.ui.workers.CleanupWorkerFactory
import com.example.notes.di.AppModule

class NotesAppApplication : Application(), Configuration.Provider {

    val appModule by lazy { AppModule(this) }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(CleanupWorkerFactory(notesRepository = appModule.provideNotesRepository()))
        .build()
}