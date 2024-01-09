package com.dojo.moovies.core.domain

import java.math.BigDecimal

enum class MooviesMediaType {
    TV, MOVIE;

    companion object {
        fun valueFromString(str: String) : MooviesMediaType {
            return when (str) {
                "tv" -> TV
                "movie" -> MOVIE
                else -> throw IllegalArgumentException("MediaType invalid")
            }
        }
    }
}

data class MooviesDataSimplified(
    val id: Long,
    val backdropPath: String?,
    val name: String,
    val originalLanguage: String,
    val originalName: String?,
    val overview: String,
    val posterPath: String?,
    val mediaType: MooviesMediaType,
    val genreList: String,
    val releaseDate: String
)