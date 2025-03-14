package com.dojo.moovies.out.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dojo.moovies.core.domain.MooviesMediaType
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

internal const val TABLE_NAME_MY_LIST = "tb_mylist"


@Entity(tableName = TABLE_NAME_MY_LIST)
data class MyListEntity(
    @PrimaryKey
    val mooviesId: String,
    val id: Long,
    val backdropPath: String?,
    val name: String,
    val originalLanguage: String,
    val originalName: String?,
    val overview: String,
    val posterPath: String?,
    val mediaType: String,
    val genreList: String,
    val releaseDate: String,
    val watched: Boolean
)