package com.dojo.moovies.core.domain


internal const val TMDB_TRAILER_API_YOUTUBE_VALUE = "YouTube"
internal const val TMDB_TRAILER_API_TRAILER_VALUE = "Trailer"
internal const val YOUTUBE_EMBED_URL = "https://www.youtube.com/embed/"

data class MooviesTrailerData(
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val id: String
)