package com.dojo.moovies.out.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dojo.moovies.out.db.entity.MyListEntity

internal const val DB_NAME = "moovies-dojo.db"

@Database(
    entities = [MyListEntity::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myListDao(): MyListDao

}