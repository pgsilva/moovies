package com.dojo.moovies.out.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val TABLE_NAME_MY_LIST = "tb_mylist"


@Entity(tableName = TABLE_NAME_MY_LIST)
data class MyListEntity(
    @PrimaryKey val id: Long,
    val isAdult: Boolean,
    val backdropPath: String,
    val name: String?,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val posterPath: String,
    val mediaType: String
)