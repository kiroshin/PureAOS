/*
 * Flow+Store.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

typealias MutableStore<T> = MutableStateFlow<T>
typealias Store<T> = StateFlow<T>
typealias Stored<T> = Flow<T>

inline fun <T, R> Flow<T>.stored(crossinline transform: suspend (value: T) -> R): Stored<R>
        = map(transform).distinctUntilChanged()

