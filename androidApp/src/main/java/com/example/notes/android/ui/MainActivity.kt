package com.example.notes.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.notes.Greeting
import com.example.notes.android.NotesAppApplication
import com.example.notes.android.ui.navigation.NotesAppNavigation
import com.example.notes.android.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // make the app edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val application = this.application as NotesAppApplication
        val appModule = application.appModule

        setContent {
            NotesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    content = { NotesAppNavigation(appModule = appModule) }
                )
            }
        }
    }
}

