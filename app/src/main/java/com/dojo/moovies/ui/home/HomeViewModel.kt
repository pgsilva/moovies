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
private const val MAX_NUMBER_PAGE = 5
private const val MIN_NUMBER_PAGE = 1

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

    private var currentPage = MIN_NUMBER_PAGE

    init {
        loadDiscoverMovies()
        loadDiscoverTv()
        loadPreviewMyList()
        loadPopularMovies()
        loadPopularTv()

        checkAllDataLoad()
    }

    fun nextContentPage() {
        if (currentPage < MAX_NUMBER_PAGE) {
            currentPage++
            loadDiscoverMovies(currentPage)
            loadDiscoverTv(currentPage)
            loadPopularMovies(currentPage)
            loadPopularTv(currentPage)

            checkAllDataLoad()
        }

    }

    fun previousContentPage() {
        if (currentPage > MIN_NUMBER_PAGE) {
            currentPage--
            loadDiscoverMovies(currentPage)
            loadDiscoverTv(currentPage)
            loadPopularMovies(currentPage)
            loadPopularTv(currentPage)

            checkAllDataLoad()
        }
    }

    private fun loadDiscoverMovies(page: Int = MIN_NUMBER_PAGE) {
        viewModelScope.launch {
            val state = interactor.loadDiscoverMovies(page)
            if (state is HomeInteractorState.HomeLoadState.Success) {
                _discoverMovieList.update { state.data }
                _loadCount.update { count -> count + 1 }
            }
        }
    }

    private fun loadDiscoverTv(page: Int = MIN_NUMBER_PAGE) {
        viewModelScope.launch {
            val state = interactor.loadDiscoverTv(page)
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

    private fun loadPopularMovies(page: Int = MIN_NUMBER_PAGE) {
        viewModelScope.launch {
            val state = interactor.loadPopularMovies(page)
            if (state is HomeInteractorState.HomeLoadState.Success) {
                _popularMovieList.update { state.data }
                _loadCount.update { count -> count + 1 }
            }

        }
    }

    private fun loadPopularTv(page: Int = MIN_NUMBER_PAGE) {
        viewModelScope.launch {
            val state = interactor.loadPopularTv(page)
            if (state is HomeInteractorState.HomeLoadState.Success) {
                _popularTvList.update { state.data }
                _loadCount.update { count -> count + 1 }
            }

        }
    }

    private fun checkAllDataLoad() {
        viewModelScope.launch {
            loadCount.collect {
                if (it == TOTAL_LISTS_TO_LOAD_FROM_API) {
                    _allDataLoad.emit(true)
                    _loadCount.update { 0 }
                }
            }
        }
    }
}