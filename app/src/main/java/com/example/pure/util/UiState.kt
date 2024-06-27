/*
 * UiState.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.util

//sealed interface UiState<out T> {
//    data object Ready : UiState<Nothing>
//    data class Loading(val permil: Int = 0) : UiState<Nothing>
//    data class Success<out T>(val value: T) : UiState<T>
//    data class Failure(val message: String) : UiState<Nothing>
//}

sealed class UiState<out T> {
    data object Ready : UiState<Nothing>()
    data class Loading(val permil: Int = 0) : UiState<Nothing>()
    data class Success<out T>(val value: T) : UiState<T>()
    data class Failure(val message: String) : UiState<Nothing>()
}

inline fun <reified T: Any> UiState<T>.onReady(action: () -> Unit) {
    if (this is UiState.Ready) {
        action()
    }
}

inline fun <reified T: Any> UiState<T>.onLoading(action: (permil: Int) -> Unit) {
    if (this is UiState.Loading) {
        action(permil)
    }
}

inline fun <reified T: Any> UiState<T>.onSuccess(action: (value: T) -> Unit) {
    if (this is UiState.Success) {
        action(value)
    }
}

inline fun <reified T: Any> UiState<T>.onFailure(action: (message: String) -> Unit) {
    if (this is UiState.Failure) {
        action(message)
    }
}

inline fun <reified T: Any> UiState<T>.onReadyOrLoading(action: (permil: Int) -> Unit) {
    if (this is UiState.Ready) { action(0) }
    if (this is UiState.Loading) { action(permil) }
}



