package com.dojo.moovies.repository.mapper

import android.annotation.SuppressLint
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType
import com.dojo.moovies.core.domain.MooviesTrailerData
import com.dojo.moovies.core.domain.MooviesWatchProvider
import com.dojo.moovies.core.domain.MooviesWatchProviders
import com.dojo.moovies.out.api.data.tmdb.Detail
import com.dojo.moovies.out.api.data.tmdb.StreamProvider
import com.dojo.moovies.out.api.data.tmdb.TrailersContent
import com.dojo.moovies.out.db.entity.MyListEntity
import java.text.SimpleDateFormat
import java.util.UUID


@SuppressLint("SimpleDateFormat")
internal fun Detail.toDomain(): MooviesDataSimplified {
    val name = this.name ?: this.title
    val originalName = this.originalName ?: this.title
    val poster = this.posterPath ?: this.backdropPath
    var releaseDate = this.releaseDate ?: this.firstAirDate

    val mediaType: MooviesMediaType = if (this.name != null) {
        MooviesMediaType.TV
    } else {
        MooviesMediaType.MOVIE
    }

    if (!releaseDate.isNullOrBlank()) {
        val formatterIn = SimpleDateFormat("yyyy-MM-dd")
        val formatterOut = SimpleDateFormat("dd/MM/yyyy")

        val date = formatterIn.parse(releaseDate)
        releaseDate = date?.let { formatterOut.format(it) }
    }

    return MooviesDataSimplified(
        id = this.id.toLong(),
        backdropPath = this.backdropPath,
        name = name ?: "No Name Provided",
        originalLanguage = this.originalLanguage ?: "en",
        originalName = originalName,
        overview = this.overview ?: "No description provided",
        posterPath = poster,
        mediaType = mediaType,
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

internal fun MooviesDataSimplified.toEntity(): MyListEntity {
    return MyListEntity(
        mooviesId = UUID.randomUUID().toString(),
        id = this.id,
        backdropPath = this.backdropPath,
        name = name,
        originalLanguage = this.originalLanguage,
        originalName = originalName,
        overview = this.overview,
        posterPath = this.posterPath,
        mediaType = MooviesMediaType.valueFromEnum(this.mediaType),
        genreList = this.genreList,
        releaseDate = this.releaseDate
    )
}

internal fun StreamProvider.toProviderDomain(): MooviesWatchProviders {
    val buy = this.buy?.map {
        MooviesWatchProvider(
            this.link,
            it.logoPath,
            it.providerId,
            it.providerName,
            it.displayPriority,

            )
    }

    val flatRate = this.flatRate?.map {
        MooviesWatchProvider(
            this.link,
            it.logoPath,
            it.providerId,
            it.providerName,
            it.displayPriority
        )
    }

    return MooviesWatchProviders(
        buy = buy,
        flatRate = flatRate,
    )
}

internal fun TrailersContent.toDomain(): MooviesTrailerData {
    return MooviesTrailerData(
        name = this.name,
        key = this.key,
        site = this.site,
        type = this.type,
        official = this.official,
        id = this.id
    )
}