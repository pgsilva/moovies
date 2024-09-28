package com.dojo.moovies.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.interactor.HomeInteractor
import com.dojo.moovies.interactor.state.HomeInteractorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TOTAL_LISTS_TO_LOAD_FROM_API = 4

class HomeViewModel(
    private val interactor: HomeInteractor
) : ViewModel() {

    private val _discoverMovieList = MutableStateFlow(emptyList<MooviesDataSimplified>())

    val discoverMovieList = _discoverMovieList.asStateFlow()

    private val _discoverTvList = MutableStateFlow(emptyList<MooviesDataSimplified>())

    val discoverTvList = _discoverTvList.asStateFlow()

    private val _previewMyList = MutableStateFlow(emptyList<MooviesDataSimplified>())

    val previewMyList = _previewMyList.asStateFlow()

    private val _popularMovieList = MutableStateFlow(emptyList<MooviesDataSimplified>())

    val popularMovieList = _popularMovieList.asStateFlow()

    private val _popularTvList = MutableStateFlow(emptyList<MooviesDataSimplified>())

    val popularTvList = _popularTvList.asStateFlow()

    private val _loadCount = MutableStateFlow(0)

    private val loadCount = _loadCount.asStateFlow()

    private val _allDataLoad = MutableStateFlow(false)

    internal val allDataLoad = _allDataLoad.asStateFlow()

    init {
        loadDiscoverMovies()
        loadDiscoverTv()
        loadPreviewMyList()
        loadPopularMovies()
        loadPopularTv()

        checkAllDataLoad()
    }


    private fun loadDiscoverMovies() {
        viewModelScope.launch {
            val state = interactor.loadDiscoverMovies()
            if (state is HomeInteractorState.HomeLoadState.Success) {
                _discoverMovieList.update { state.data }
                _loadCount.update { count -> count + 1 }
            }
        }
    }

    private fun loadDiscoverTv() {
        viewModelScope.launch {
            val state = interactor.loadDiscoverTv()
            if (state is HomeInteractorState.HomeLoadState.Success) {
                _discoverTvList.update { state.data }
                _loadCount.update { count -> count + 1 }

            }
        }
    }

    private fun loadPreviewMyList() {
        viewModelScope.launch {
            interactor.loadPreviewMyList()
                .flowOn(Dispatchers.IO)
                .collect { state ->
                    if (state is HomeInteractorState.HomeLoadState.Success) {
                        _previewMyList.update { state.data }
                    }
                }
        }
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            val state = interactor.loadPopularMovies()
            if (state is HomeInteractorState.HomeLoadState.Success) {
                _popularMovieList.update { state.data }
                _loadCount.update { count -> count + 1 }
            }

        }
    }

    private fun loadPopularTv() {
        viewModelScope.launch {
            val state = interactor.loadPopularTv()
            if (state is HomeInteractorState.HomeLoadState.Success) {
                _popularTvList.update { state.data }
                _loadCount.update { count -> count + 1 }
            }

        }
    }

    private fun checkAllDataLoad() {
        viewModelScope.launch {
            loadCount.collect {
                if (it == TOTAL_LISTS_TO_LOAD_FROM_API)
                    _allDataLoad.emit(true)
            }
        }
    }
}