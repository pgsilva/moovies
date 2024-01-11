package com.dojo.moovies.repository

import android.util.Log
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesWatchProviders
import com.dojo.moovies.interactor.state.DetailInteractorState
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.api.data.tmdb.DiscoverMovieResponse
import com.dojo.moovies.out.api.data.tmdb.DiscoverTvResponse
import com.dojo.moovies.out.api.data.tmdb.Detail
import com.dojo.moovies.out.api.data.tmdb.MultiResponse
import com.dojo.moovies.out.api.data.tmdb.StreamResponse
import com.dojo.moovies.repository.mapper.RetrofitCommons.extractResponse
import com.dojo.moovies.repository.mapper.toDomain
import com.dojo.moovies.repository.mapper.toProviderDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class TheMovieDbRepository(
    private val api: TheMovieDbApi
) {

    suspend fun getDiscoverMovies(): List<MooviesDataSimplified> {
        val response: DiscoverMovieResponse

        withContext(Dispatchers.IO) {
            response = api.getDiscoverMovies().extractResponse()
        }

        return response.results.map { it.toDomain() }
    }

    suspend fun getDiscoverTv(): List<MooviesDataSimplified> {
        val response: DiscoverTvResponse

        withContext(Dispatchers.IO) {
            response = api.getDiscoverTv().extractResponse()
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

    suspend fun getPopularMovies(): List<MooviesDataSimplified> {
        val response: DiscoverMovieResponse

        withContext(Dispatchers.IO) {
            response = api.getTopRatedMovies().extractResponse()
        }

        return response.results.map { it.toDomain() }
    }

    suspend fun getPopularTv(): List<MooviesDataSimplified> {
        val response: DiscoverTvResponse

        withContext(Dispatchers.IO) {
            response = api.getTopRatedTv().extractResponse()
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

}