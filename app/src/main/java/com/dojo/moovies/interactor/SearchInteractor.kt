package com.dojo.moovies.interactor

import com.dojo.moovies.interactor.state.SearchInteractorState
import com.dojo.moovies.repository.TheMovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchInteractor(
    private val apiRepository: TheMovieDbRepository
) {
    suspend fun findBy(query: String): Flow<SearchInteractorState.SearchState> = flow {
        apiRepository.getMultiByQuery(query).let {
            it.collect { list ->
                if (list.isEmpty()) emit(SearchInteractorState.SearchState.Error)
                else emit(SearchInteractorState.SearchState.Success(list))
            }
        }
    }
}
