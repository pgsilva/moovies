package com.dojo.moovies.core.domain

import okhttp3.MediaType
import java.math.BigDecimal

enum class MooviesMediaType {
    TV, MOVIE
}

internal data class MooviesData(
    val id: Long,
    val isAdult: Boolean,
    val backdropPath: String,
    val name: String?,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val posterPath: String,
    val mediaType: MediaType?,
    val voteAverage: BigDecimal,
    val voteCount: Int
)