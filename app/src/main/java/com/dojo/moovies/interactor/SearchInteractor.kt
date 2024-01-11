package com.dojo.moovies.interactor

import android.util.Log
import com.dojo.moovies.interactor.state.HomeInteractorState
import com.dojo.moovies.interactor.state.SearchInteractorState
import com.dojo.moovies.repository.TheMovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchInteractor(
    private val apiRepository: TheMovieDbRepository
) {
    suspend fun findBy(query: String): SearchInteractorState.SearchState = try {
        apiRepository.getMultiByQuery(query).let {
            when {
                it.isNotEmpty() -> SearchInteractorState.SearchState.Success(it)
                else -> SearchInteractorState.SearchState.Error
            }
        }
    } catch (e: Exception) {
        Log.e(
            "MOOVIES-THEMOVIEDBAPI",
            "Api Search Multi Error, response is not successful: ${e.printStackTrace()}"
        )
        SearchInteractorState.SearchState.Error
    }
}
