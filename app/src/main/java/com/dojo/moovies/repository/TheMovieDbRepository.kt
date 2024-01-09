package com.dojo.moovies.repository

import android.util.Log
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesProvider
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.repository.mapper.toDomain
import com.dojo.moovies.repository.mapper.toProviderDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//TODO Implementar uma facotory de interface para separar domain do repo
class TheMovieDbRepository(
    private val api: TheMovieDbApi
) {

    suspend fun getDiscoverMovies(): Flow<List<MooviesDataSimplified>> = flow {
        val response = api.getDiscoverMovies()
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                val domain = response.results.map { it.toDomain() }
                emit(domain)
            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Discover Movies Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )

            emit(emptyList())
        }
    }

    suspend fun getDiscoverTv(): Flow<List<MooviesDataSimplified>> = flow {
        val response = api.getDiscoverTv()
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                val domain = response.results.map { it.toDomain() }
                emit(domain)
            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Discover TV Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )

            emit(emptyList())
        }
    }

    suspend fun getMultiByQuery(query: String): Flow<List<MooviesDataSimplified>> = flow {
        val response = api.getMultiByQuery(query)
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                val domain = response.results.map { it.toDomain() }
                emit(domain)
            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Multi Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )

            emit(emptyList())
        }
    }

    fun getPopularMovies(): Flow<List<MooviesDataSimplified>> = flow {
        val response = api.getTopRatedMovies()
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                val domain = response.results.map { it.toDomain() }
                emit(domain)
            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Discover TV Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )

            emit(emptyList())
        }
    }

    fun getPopularTv(): Flow<List<MooviesDataSimplified>> = flow {
        val response = api.getTopRatedTv()
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                val domain = response.results.map { it.toDomain() }
                emit(domain)
            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Discover TV Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )

            emit(emptyList())
        }
    }

    fun getMovie(id: Int): Flow<MooviesDataSimplified?> = flow {
        val response = api.getMovieDetail(id)
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                val domain = response.toDomain()
                emit(domain)
            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Movie By Id Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )
            emit(null)
        }
    }

    fun getTv(id: Int): Flow<MooviesDataSimplified?> = flow {
        val response = api.getTvDetail(id)
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                val domain = response.toDomain()
                emit(domain)
            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Tv by Id Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )
            emit(null)
        }
    }

    fun getMovieStreaming(id: Int): Flow<MooviesProvider?> = flow {
        val response = api.getMovieStreaming(id)
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                response.results.br?.let {
                    val domain = it.toProviderDomain()
                    emit(domain)
                }
            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Tv by Id Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )
            emit(null)
        }
    }

    fun getTvStreaming(id: Int): Flow<MooviesProvider?> = flow {
        val response = api.getTvStreaming(id)
        if (response.isSuccessful) {
            response.body()!!.let { response ->
                response.results.br?.let {
                    val domain = it.toProviderDomain()
                    emit(domain)
                }

            }
        } else {
            Log.e(
                "MOOVIES-THEMOVIEDBAPI",
                "Api Tv by Id Error, response is not successful: ${
                    response.errorBody().toString()
                }"
            )
            emit(null)
        }
    }

}