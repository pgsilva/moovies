package com.dojo.moovies.interactor

import com.dojo.moovies.interactor.state.HomeInteractorState.HomeLoadState
import com.dojo.moovies.out.db.MyListDao
import com.dojo.moovies.repository.MyListRepository
import com.dojo.moovies.repository.TheMovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeIntercator(
    private val apiRepository: TheMovieDbRepository,
    private val dbRepository: MyListRepository
) {

    suspend fun loadDiscoverMovies(): Flow<HomeLoadState> = flow {
        apiRepository.getDiscoverMovies().let {
            it.collect { list ->
                if (list.isEmpty()) emit(HomeLoadState.Error)
                else emit(HomeLoadState.Success(list))
            }
        }
    }

    suspend fun loadDiscoverTv(): Flow<HomeLoadState> = flow {
        apiRepository.getDiscoverTv().let {
            it.collect { list ->
                if (list.isEmpty()) emit(HomeLoadState.Error)
                else emit(HomeLoadState.Success(list))
            }
        }
    }

    suspend fun loadPreviewMyList(): Flow<HomeLoadState> = flow {
        dbRepository.findAll().collect {
            emit(HomeLoadState.Success(it))
        }
    }
}
