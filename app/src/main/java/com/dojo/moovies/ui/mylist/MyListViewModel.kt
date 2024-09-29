package com.dojo.moovies.ui.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.interactor.MyListInteractor
import com.dojo.moovies.interactor.state.MyListInteractorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyListViewModel(
    private val interactor: MyListInteractor
) : ViewModel() {

    private val _myList = MutableStateFlow(emptyList<MooviesDataSimplified>())

    val myList = _myList.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            interactor.loadPreviewMyList()
                .flowOn(Dispatchers.IO)
                .collect { state ->
                    if (state is MyListInteractorState.MyListState.Success)
                        _myList.update { state.data }
                }
        }
    }

    fun markAsWatched(item: MooviesDataSimplified) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.markAsWatched(item)
        }
    }

    fun markAsNotWatched(item: MooviesDataSimplified) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.markAsNotWatched(item)
        }
    }

}