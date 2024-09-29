package com.dojo.moovies.interactor

import android.util.Log
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.core.domain.MooviesMediaType
import com.dojo.moovies.interactor.state.DetailInteractorState
import com.dojo.moovies.interactor.state.DetailInteractorState.StreamingListState
import com.dojo.moovies.repository.MyListRepository
import com.dojo.moovies.repository.TheMovieDbRepository

class DetailInteractor(
    private val apiRepository: TheMovieDbRepository,
    private val myListRepository: MyListRepository
) {

    suspend fun load(map: Pair<Int, String>): DetailInteractorState.DetailState = try {
        when (MooviesMediaType.valueFromString(map.second)) {
            MooviesMediaType.MOVIE -> {
                val detail = apiRepository.getMovie(map.first)
                DetailInteractorState.DetailState.Success(detail)
            }

            MooviesMediaType.TV -> {
                val detail = apiRepository.getTv(map.first)
                DetailInteractorState.DetailState.Success(detail)
            }
        }
    } catch (e: Exception) {
        Log.e(
            "MOOVIES-THEMOVIEDBAPI",
            "Api Detail Error, response is not successful: ${e.printStackTrace()}"
        )
        DetailInteractorState.DetailState.Error
    }


    suspend fun loadStreaming(map: Pair<Int, String>): StreamingListState = try {
        when (MooviesMediaType.valueFromString(map.second)) {
            MooviesMediaType.MOVIE -> {
                val list = apiRepository.getMovieStreaming(map.first)
                if (list == null) StreamingListState.Error
                else StreamingListState.Success(list)
            }

            MooviesMediaType.TV -> {
                val list = apiRepository.getTvStreaming(map.first)
                if (list == null) StreamingListState.Error
                else StreamingListState.Success(list)
            }
        }
    } catch (e: Exception) {
        Log.e(
            "MOOVIES-THEMOVIEDBAPI",
            "Api Streaming List Error, response is not successful: ${e.printStackTrace()}"
        )
        StreamingListState.Error
    }

    suspend fun loadMyListByIdAndMediaType(detailMap: Pair<Int, String>): DetailInteractorState.MyListState =
        try {
            myListRepository.findByIdAndMediaType(detailMap).let {
                DetailInteractorState.MyListState.Success(it)
            }

        } catch (e: Exception) {
            Log.e(
                "MOOVIES-DATABASE",
                "Database Find My List Error, response is not successful: ${e.printStackTrace()}"
            )
            DetailInteractorState.MyListState.Error
        }

    suspend fun loadTrailer(map: Pair<Int, String>): DetailInteractorState.TrailerState = try {
        when (MooviesMediaType.valueFromString(map.second)) {
            MooviesMediaType.MOVIE -> {
                val trailer = apiRepository.getTrailerMovie(map.first)
                DetailInteractorState.TrailerState.Success(trailer)
            }

            MooviesMediaType.TV -> {
                val trailer = apiRepository.getTrailerTv(map.first)
                DetailInteractorState.TrailerState.Success(trailer)
            }
        }
    } catch (e: Exception) {
        Log.e(
            "MOOVIES-DATABASE",
            "Api Video List Error, response is not successful: ${e.printStackTrace()}"
        )
        DetailInteractorState.TrailerState.Error
    }

    suspend fun saveInMyList(mooviesDataSimplified: MooviesDataSimplified) {
        myListRepository.upsert(mooviesDataSimplified)
    }

    suspend fun removeFromMyList(mooviesDataSimplified: MooviesDataSimplified) {
        myListRepository.remove(mooviesDataSimplified)
    }


    suspend fun loadSimilar(map: Pair<Int, String>): DetailInteractorState.SimilarListState =
        try {
            when (MooviesMediaType.valueFromString(map.second)) {
                MooviesMediaType.MOVIE -> {
                    val similarList = apiRepository.getSimilarMovie(map.first, page = 1)
                    DetailInteractorState.SimilarListState.Success(similarList)
                }

                MooviesMediaType.TV -> {
                    val similarList = apiRepository.getSimilarTv(map.first, page = 1)
                    DetailInteractorState.SimilarListState.Success(similarList)
                }
            }
        } catch (e: Exception) {
            Log.e(
                "MOOVIES-DATABASE",
                "Api Similar Error, response is not successful: ${e.printStackTrace()}"
            )
            DetailInteractorState.SimilarListState.Error
        }
}


