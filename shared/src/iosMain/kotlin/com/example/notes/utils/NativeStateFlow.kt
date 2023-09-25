package com.example.notes.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


actual class NativeStateFlow<T> actual constructor(source: Flow<T>) : Flow<T> by source {
    fun collect(onCollect: (T) -> Unit): DisposableHandle {
        val scope = MainScope().apply {
            launch(Dispatchers.Main) { collect(onCollect) }
        }
        return DisposableHandle { scope.cancel() }
    }
}