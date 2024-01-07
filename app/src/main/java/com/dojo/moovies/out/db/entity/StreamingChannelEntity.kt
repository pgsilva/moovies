package com.dojo.moovies.out.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val TABLE_NAME_STREAMING_INFO = "tb_streaminginfo"

@Entity(tableName = TABLE_NAME_STREAMING_INFO)
data class StreamingChannelEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val homePage: String?,
    val themeColorCode: String?,
    val lightThemeImage: String,
    val darkThemeImage: String,
    val whiteImage: String
)