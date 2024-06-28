/*
 * DataStore+ReadWrite.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


val Context.mxStorage: DataStore<Preferences> by preferencesDataStore(name = "settings")

suspend inline fun <T> DataStore<Preferences>.mxReadValue(key: Preferences.Key<T>, defaultValue: T): T {
    return data.catch { mxRecoverOrThrow(it) }.map { it[key] }.firstOrNull() ?: defaultValue
}

suspend inline fun <T> DataStore<Preferences>.mxReadValue(key: Preferences.Key<T>): T? {
    return data.catch { mxRecoverOrThrow(it) }.map { it[key] }.firstOrNull()
}

suspend fun FlowCollector<Preferences>.mxRecoverOrThrow(throwable: Throwable) {
    if (throwable is IOException) { emit(emptyPreferences()) } else { throw throwable }
    // IOException 발생 시 빈 프리퍼런스 반환
}

suspend inline fun <T> DataStore<Preferences>.mxWriteValue(key: Preferences.Key<T>, value: T?) = edit {
    if (value == null) it.remove(key) else it[key] = value
    // null 입력 시 키 삭제
}