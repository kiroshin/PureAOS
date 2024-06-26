/*
 * RoomStack.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.util

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object RoomStack {
    fun <T : RoomDatabase> getCoreForFile(context: Context, klass: Class<T>, name: String?): T {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = klass,
            name = name
        ).fallbackToDestructiveMigration().build()
    }

    fun <T : RoomDatabase> getCoreForMem(context: Context, klass: Class<T>): T {
        return Room.inMemoryDatabaseBuilder(
            context = context.applicationContext,
            klass = klass
        ).fallbackToDestructiveMigration().build()
    }
}

