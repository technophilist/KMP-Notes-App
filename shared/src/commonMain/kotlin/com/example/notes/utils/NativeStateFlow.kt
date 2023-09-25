package com.example.notes.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * A cross platform implementation of [StateFlow].
 */
expect class NativeStateFlow<T>(source: Flow<T>) : Flow<T>

/**
 * Used to convert a [StateFlow] to a [NativeStateFlow]
 */
fun <T> StateFlow<T>.asNativeStateFlow() = NativeStateFlow(this)