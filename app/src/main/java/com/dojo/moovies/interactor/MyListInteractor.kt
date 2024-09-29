package com.dojo.moovies.interactor

import android.util.Log
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.interactor.state.MyListInteractorState
import com.dojo.moovies.repository.MyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyListInteractor(
    private val dbRepository: MyListRepository
) {
    suspend fun loadPreviewMyList(): Flow<MyListInteractorState.MyListState> = flow {
        dbRepository.findAll().collect {
            emit(MyListInteractorState.MyListState.Success(it))
        }
    }

    suspend fun markAsWatched(item: MooviesDataSimplified) {
        try {
            dbRepository.updateWatched(item.copy(watched = true))
        } catch (e: Exception) {
            Log.e(
                "MOOVIES-ROOM",
                "Error updating item in database: ${e.printStackTrace()}"
            )
        }
    }

    suspend fun markAsNotWatched(item: MooviesDataSimplified) {
       try {
            dbRepository.updateWatched(item.copy(watched = false))
        } catch (e: Exception) {
            Log.e(
                "MOOVIES-ROOM",
                "Error updating item in database: ${e.printStackTrace()}"
            )
       }
    }
}

