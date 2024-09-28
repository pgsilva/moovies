package com.dojo.moovies.interactor

import android.util.Log
import com.dojo.moovies.interactor.state.HomeInteractorState.HomeLoadState
import com.dojo.moovies.repository.MyListRepository
import com.dojo.moovies.repository.TheMovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeInteractor(
    private val apiRepository: TheMovieDbRepository,
    private val dbRepository: MyListRepository
) {

    suspend fun loadDiscoverMovies(): HomeLoadState = try {
        apiRepository.getDiscoverMovies(page = 1).let {
            when {
                it.isNotEmpty() -> HomeLoadState.Success(it)
                else -> HomeLoadState.Error
            }
        }
    } catch (e: Exception) {
        Log.e(
            "MOOVIES-THEMOVIEDBAPI",
            "Api Discover Movie Error, response is not successful: ${e.printStackTrace()}"
        )
        HomeLoadState.Error
    }

    suspend fun loadDiscoverTv(): HomeLoadState = try {
        apiRepository.getDiscoverTv(page = 1).let {
            when {
                it.isNotEmpty() -> HomeLoadState.Success(it)
                else -> HomeLoadState.Error
            }
        }
    } catch (e: Exception) {
        Log.e(
            "MOOVIES-THEMOVIEDBAPI",
            "Api Discover TV Error, response is not successful: ${e.printStackTrace()}"
        )
        HomeLoadState.Error
    }


    suspend fun loadPreviewMyList(): Flow<HomeLoadState> = flow {
        dbRepository.findAllLimit20().collect {
            emit(HomeLoadState.Success(it))
        }
    }

    suspend fun loadPopularMovies(
    ): HomeLoadState = try {
        apiRepository.getPopularMovies(page = 1).let {
            when {
                it.isNotEmpty() -> HomeLoadState.Success(it)
                else -> HomeLoadState.Error
            }
        }
    } catch (e: Exception) {
        Log.e(
            "MOOVIES-THEMOVIEDBAPI",
            "Api Popular Movies Error, response is not successful: ${e.printStackTrace()}"
        )
        HomeLoadState.Error
    }

    suspend fun loadPopularTv(
    ): HomeLoadState = try {
        apiRepository.getPopularTv(1).let {
            when {
                it.isNotEmpty() -> HomeLoadState.Success(it)
                else -> HomeLoadState.Error
            }
        }
    } catch (e: Exception) {
        Log.e(
            "MOOVIES-THEMOVIEDBAPI",
            "Api Popular Tv Error, response is not successful: ${e.printStackTrace()}"
        )
        HomeLoadState.Error
    }
}