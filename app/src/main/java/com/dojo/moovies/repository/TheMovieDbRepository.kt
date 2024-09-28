package com.dojo.moovies.repository

import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesTrailerData
import com.dojo.moovies.core.domain.MooviesWatchProviders
import com.dojo.moovies.core.domain.TMDB_TRAILER_API_TRAILER_VALUE
import com.dojo.moovies.core.domain.TMDB_TRAILER_API_YOUTUBE_VALUE
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.api.data.tmdb.Detail
import com.dojo.moovies.out.api.data.tmdb.DiscoverMovieResponse
import com.dojo.moovies.out.api.data.tmdb.DiscoverTvResponse
import com.dojo.moovies.out.api.data.tmdb.MultiResponse
import com.dojo.moovies.out.api.data.tmdb.StreamResponse
import com.dojo.moovies.out.api.data.tmdb.TrailerResponse
import com.dojo.moovies.repository.mapper.RetrofitCommons.extractResponse
import com.dojo.moovies.repository.mapper.toDomain
import com.dojo.moovies.repository.mapper.toProviderDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class TheMovieDbRepository(
    private val api: TheMovieDbApi
) {

    suspend fun getDiscoverMovies(page: Int): List<MooviesDataSimplified> {
        val response: DiscoverMovieResponse

        withContext(Dispatchers.IO) {
            response = api.getDiscoverMovies(
                page = page
            ).extractResponse()
        }

        return response.results.map { it.toDomain() }
    }

    suspend fun getDiscoverTv(page: Int): List<MooviesDataSimplified> {
        val response: DiscoverTvResponse

        withContext(Dispatchers.IO) {
            response = api.getDiscoverTv(
                page = page
            ).extractResponse()
        }

        return response.results.map { it.toDomain() }
    }

    suspend fun getMultiByQuery(query: String): List<MooviesDataSimplified> {
        val response: MultiResponse

        withContext(Dispatchers.IO) {
            response = api.getMultiByQuery(query).extractResponse()
        }

        return response.results.map { it.toDomain() }
    }

    suspend fun getPopularMovies(page: Int): List<MooviesDataSimplified> {
        val response: DiscoverMovieResponse

        withContext(Dispatchers.IO) {
            response = api.getTopRatedMovies(
                page = page
            ).extractResponse()
        }

        return response.results.map { it.toDomain() }
    }

    suspend fun getPopularTv(page: Int): List<MooviesDataSimplified> {
        val response: DiscoverTvResponse

        withContext(Dispatchers.IO) {
            response = api.getTopRatedTv(
                page = page
            ).extractResponse()
        }

        return response.results.map { it.toDomain() }
    }

    suspend fun getMovie(id: Int): MooviesDataSimplified {
        val response: Detail

        withContext(Dispatchers.IO) {
            response = api.getMovieDetail(id).extractResponse()
        }

        return response.toDomain()
    }

    suspend fun getTv(id: Int): MooviesDataSimplified {
        val response: Detail

        withContext(Dispatchers.IO) {
            response = api.getTvDetail(id).extractResponse()
        }

        return response.toDomain()
    }

    suspend fun getMovieStreaming(id: Int): MooviesWatchProviders? {
        val response: StreamResponse

        withContext(Dispatchers.IO) {
            response = api.getMovieStreaming(id).extractResponse()
        }

        return response.results.br?.toProviderDomain()
    }

    suspend fun getTvStreaming(id: Int): MooviesWatchProviders? {
        val response: StreamResponse

        withContext(Dispatchers.IO) {
            response = api.getTvStreaming(id).extractResponse()
        }

        return response.results.br?.toProviderDomain()
    }

    suspend fun getTrailerMovie(id: Int): MooviesTrailerData? {
        val response: TrailerResponse

        withContext(Dispatchers.IO) {
            response = api.getTrailerMovie(id).extractResponse()
        }

        val videos = response.results.filter {
            (it.site == TMDB_TRAILER_API_YOUTUBE_VALUE) and
                    (it.type == TMDB_TRAILER_API_TRAILER_VALUE) and
                    (it.official)
        }

        return videos.firstOrNull()?.toDomain()
    }

    suspend fun getTrailerTv(id: Int): MooviesTrailerData? {
        val response: TrailerResponse

        withContext(Dispatchers.IO) {
            response = api.getTrailerTv(id).extractResponse()
        }

        val videos = response.results.filter {
            (it.site == TMDB_TRAILER_API_YOUTUBE_VALUE) and
                    (it.type == TMDB_TRAILER_API_TRAILER_VALUE) and
                    (it.official)
        }

        return videos.firstOrNull()?.toDomain()
    }


}