package com.example.notes.android

import android.app.Application
import com.example.notes.di.AppModule

class NotesAppApplication : Application() {

    val appModule by lazy { AppModule(this) }
}