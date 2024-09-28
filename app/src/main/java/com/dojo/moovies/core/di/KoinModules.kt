package com.dojo.moovies.core.di

import androidx.room.Room
import com.dojo.moovies.interactor.DetailInteractor
import com.dojo.moovies.interactor.HomeInteractor
import com.dojo.moovies.interactor.MyListInteractor
import com.dojo.moovies.interactor.SearchInteractor
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.api.TheMovieDbApi.Companion.TMDB_API_URL
import com.dojo.moovies.out.db.AppDatabase
import com.dojo.moovies.out.db.DB_NAME
import com.dojo.moovies.repository.MyListRepository
import com.dojo.moovies.repository.TheMovieDbRepository
import com.dojo.moovies.ui.detail.DetailViewModel
import com.dojo.moovies.ui.home.HomeViewModel
import com.dojo.moovies.ui.mylist.MyListViewModel
import com.dojo.moovies.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val tmdbApiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(TMDB_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDbApi::class.java)
    }
}


val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().myListDao() }
}

val repositoryModule = module {
    factory { TheMovieDbRepository(get()) }
    factory { MyListRepository(get()) }
}

val homeViewModel = module {
    viewModel {
        val homeInteractor = HomeInteractor(get(), get())
        HomeViewModel(homeInteractor)
    }
}

val searchViewModel = module {
    viewModel {
        val searchIntercator = SearchInteractor(get())
        SearchViewModel(searchIntercator)
    }
}

val detailViewModel = module {
    viewModel {
        val detailInteractor = DetailInteractor(get(), get())
        DetailViewModel(detailInteractor)
    }
}

val myListViewModel = module {
    viewModel {
        val myListInteractor = MyListInteractor(get())
        MyListViewModel(myListInteractor)
    }
}