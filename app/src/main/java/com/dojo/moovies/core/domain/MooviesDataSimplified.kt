package com.dojo.moovies.core.domain

enum class MooviesMediaType {
    TV, MOVIE;

    companion object {
        fun valueFromString(str: String): MooviesMediaType {
            return when (str) {
                "tv" -> TV
                "movie" -> MOVIE
                else -> throw IllegalArgumentException("MediaType invalid")
            }
        }

        fun valueFromEnum(enum: MooviesMediaType): String {
            return when (enum) {
                TV -> "tv"
                MOVIE -> "movie"
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
    val releaseDate: String,
    val watched: Boolean
)