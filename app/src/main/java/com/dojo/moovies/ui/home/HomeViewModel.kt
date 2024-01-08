package com.dojo.moovies.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dojo.moovies.core.domain.MooviesData
import com.dojo.moovies.interactor.HomeIntercator
import com.dojo.moovies.interactor.state.HomeInteractorState
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val interactor: HomeIntercator
) : ViewModel() {

    private val _discoverMovieList = MutableStateFlow(emptyList<MooviesData>())

    val discoverMovieList = _discoverMovieList.asStateFlow()


    private val _discoverTvList = MutableStateFlow(emptyList<MooviesData>())

    val discoverTvList = _discoverTvList.asStateFlow()


    private val _previewMyList = MutableStateFlow(emptyList<MooviesData>())

    val previewMyList = _previewMyList.asStateFlow()


    init {
        loadDiscoverMovies()
        loadDiscoverTv()
        loadPreviewMyList()
    }

    private fun loadDiscoverMovies() {
        viewModelScope.launch {
            interactor.loadDiscoverMovies()
                .flowOn(Dispatchers.IO)
                .collect { state ->
                    if (state is HomeInteractorState.HomeLoadState.Success)
                        _discoverMovieList.update { state.data }
                }
        }
    }

    private fun loadDiscoverTv() {
        viewModelScope.launch {
            interactor.loadDiscoverTv()
                .flowOn(Dispatchers.IO)
                .collect { state ->
                    if (state is HomeInteractorState.HomeLoadState.Success)
                        _discoverTvList.update { state.data }
                }
        }
    }

    private fun loadPreviewMyList() {
        viewModelScope.launch {
            interactor.loadPreviewMyList()
                .flowOn(Dispatchers.IO)
                .collect { state ->
                    if (state is HomeInteractorState.HomeLoadState.Success)
                        _previewMyList.update { state.data }
                }
        }
    }


}