/*
 * PureDatabase.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.worker

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.pure.util.RoomStack

private const val DB_NAME = "pure_database"
private const val TABLE_NAME = "human"


@Database(entities = [(PureDatabase.HumanMO::class)], version = 1)
abstract class PureDatabase: RoomDatabase() {
    abstract fun getAccess(): Access

    companion object {
        @Volatile private var INSTANCE: PureDatabase? = null
        fun getCore(context: Context): PureDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RoomStack
                .getCoreForFile(context, PureDatabase::class.java, DB_NAME)
                .also { INSTANCE = it }
        }
    }

    @Entity(tableName = TABLE_NAME)
    data class HumanMO (
        @PrimaryKey(autoGenerate = false) val id: String,
        val name: String,
        val username: String,
        val gender: String,
        val email: String,
        val age: Int,
        val country: String,
        val cellphone: String?,
        val photo: String?
    ) {
        data class Meta (
            val id: String,
            val name: String,
            val country: String
        )
    }

    @Dao
    interface Access {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun create(model: HumanMO)

        @Query("SELECT * FROM $TABLE_NAME WHERE id = :id LIMIT 1")
        fun read(id: String): HumanMO

        @Query("SELECT * FROM $TABLE_NAME")
        fun readAll(): List<HumanMO>

        @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
        fun readAll(id: String): List<HumanMO>

        @Query("SELECT id, name, country FROM $TABLE_NAME WHERE id = :id LIMIT 1")
        fun readMeta(id: String): HumanMO.Meta

        @Query("SELECT id, name, country FROM $TABLE_NAME WHERE name LIKE '%' || :name || '%'")
        fun readMetaAllMatchName(name: String): List<HumanMO.Meta>

        @Query("SELECT id, name, country FROM $TABLE_NAME")
        fun readAllMeta(): List<HumanMO.Meta>

        @Query("SELECT id, name, country FROM $TABLE_NAME WHERE id = :id")
        fun readAllMeta(id: String): List<HumanMO.Meta>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun update(model: HumanMO)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun updateAll(models: List<HumanMO>)

        @Delete
        fun delete(model: HumanMO)

        @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
        fun delete(id: String)

        @Query("DELETE FROM $TABLE_NAME")
        fun deleteAll()

        @Query("DELETE FROM $TABLE_NAME WHERE id in (:ids)")
        fun deleteAll(ids: List<String>)

    }
}

