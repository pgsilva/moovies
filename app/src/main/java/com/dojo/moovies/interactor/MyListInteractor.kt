package com.dojo.moovies.interactor

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
}

