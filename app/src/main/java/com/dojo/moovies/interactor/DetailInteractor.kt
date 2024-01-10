package com.dojo.moovies.interactor

import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType
import com.dojo.moovies.interactor.state.DetailInteractorState
import com.dojo.moovies.interactor.state.DetailInteractorState.DetailStreamingListState
import com.dojo.moovies.repository.MyListRepository
import com.dojo.moovies.repository.TheMovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailInteractor(
    private val apiRepository: TheMovieDbRepository,
    private val myListRepository: MyListRepository
) {

    suspend fun load(map: Pair<Int, String>): Flow<DetailInteractorState.DetailState> =
        flow {
            when (MooviesMediaType.valueFromString(map.second)) {
                MooviesMediaType.MOVIE -> {
                    apiRepository.getMovie(map.first).let {
                        it.collect { detail ->
                            if (detail == null) emit(DetailInteractorState.DetailState.Error)
                            else emit(DetailInteractorState.DetailState.Success(detail))
                        }
                    }
                }

                MooviesMediaType.TV -> {
                    apiRepository.getTv(map.first).let {
                        it.collect { detail ->
                            if (detail == null) emit(DetailInteractorState.DetailState.Error)
                            else emit(DetailInteractorState.DetailState.Success(detail))
                        }
                    }
                }
            }

        }

    suspend fun loadStreaming(map: Pair<Int, String>): Flow<DetailStreamingListState> =
        flow {
            when (MooviesMediaType.valueFromString(map.second)) {
                MooviesMediaType.MOVIE -> {
                    apiRepository.getMovieStreaming(map.first).let {
                        it.collect { detail ->
                            if (detail == null) emit(DetailStreamingListState.Error)
                            else emit(DetailStreamingListState.Success(detail))
                        }
                    }
                }

                MooviesMediaType.TV -> {
                    apiRepository.getTvStreaming(map.first).let {
                        it.collect { detail ->
                            if (detail == null) emit(DetailStreamingListState.Error)
                            else emit(DetailStreamingListState.Success(detail))
                        }
                    }
                }
            }

        }

    fun loadMyListByIdAndMediaType(detailMap: Pair<Int, String>): Flow<DetailInteractorState.MyListState> = flow {
            myListRepository.findByIdAndMediaType(detailMap).collect { myList ->
                emit(DetailInteractorState.MyListState.Success(myList))
            }
        }


    suspend fun saveInMyList(mooviesDataSimplified: MooviesDataSimplified) {
        myListRepository.upsert(mooviesDataSimplified)
    }

    suspend fun removeFromMyList(mooviesDataSimplified: MooviesDataSimplified) {
        myListRepository.remove(mooviesDataSimplified)
    }
}


