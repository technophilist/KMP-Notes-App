package com.example.notes.utils

import kotlinx.coroutines.flow.Flow

actual class NativeStateFlow<T> actual constructor(source: Flow<T>) : Flow<T> by source