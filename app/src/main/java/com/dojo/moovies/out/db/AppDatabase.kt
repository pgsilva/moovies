package com.dojo.moovies.out.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.out.db.entity.StreamingChannelEntity

internal const val DB_NAME = "moovies-dojo.db"

@Database(
    entities = [MyListEntity::class, StreamingChannelEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myListDao(): MyListDao

    abstract fun streamingChannelDao(): StreamingChannelDao

}