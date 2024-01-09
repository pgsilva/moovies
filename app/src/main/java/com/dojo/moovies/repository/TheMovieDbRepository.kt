package com.dojo.moovies.repository

import android.util.Log
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.repository.mapper.toDomain
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
}