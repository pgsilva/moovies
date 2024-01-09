package com.dojo.moovies.repository.mapper

import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType
import com.dojo.moovies.out.api.data.tmdb.MovieDetail
import com.dojo.moovies.out.db.entity.MyListEntity
import java.text.SimpleDateFormat


internal fun MovieDetail.toDomain(): MooviesDataSimplified {
    val name = this.name ?: this.title
    val originalName = this.originalName ?: this.title
    val poster = this.posterPath ?: this.backdropPath
    var releaseDate = this.releaseDate ?: this.firstAirDate

    if (!releaseDate.isNullOrBlank()) {
        val formatterIn = SimpleDateFormat("yyyy-MM-dd")
        val formatterOut = SimpleDateFormat("dd/MM/yyyy")

        val date = formatterIn.parse(releaseDate)
        releaseDate = formatterOut.format(date)
    }

    return MooviesDataSimplified(
        id = this.id.toLong(),
        backdropPath = this.backdropPath,
        name = name ?: "No Name Provided",
        originalLanguage = this.originalLanguage ?: "en",
        originalName = originalName,
        overview = this.overview ?: "No description provided",
        posterPath = poster,
        mediaType = MooviesMediaType.TV,
        genreList = this.genres?.joinToString(" - ") { it.name } ?: "",
        releaseDate = releaseDate ?: "No Release Date Provided"
    )
}

internal fun MyListEntity.toDomain(): MooviesDataSimplified {

    return MooviesDataSimplified(
        id = this.id,
        backdropPath = this.backdropPath,
        name = name,
        originalLanguage = this.originalLanguage,
        originalName = originalName,
        overview = this.overview,
        posterPath = this.posterPath,
        mediaType = MooviesMediaType.valueFromString(this.mediaType),
        genreList = this.genreList,
        releaseDate = this.releaseDate
    )
}