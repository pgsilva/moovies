package com.dojo.moovies.core.di

import androidx.room.Room
import com.dojo.moovies.interactor.HomeIntercator
import com.dojo.moovies.out.api.MovieOfTheNightApi
import com.dojo.moovies.out.api.MovieOfTheNightApi.Companion.MOTN_API_URL
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.api.TheMovieDbApi.Companion.TMDB_API_URL
import com.dojo.moovies.out.db.AppDatabase
import com.dojo.moovies.out.db.DB_NAME
import com.dojo.moovies.repository.MyListRepository
import com.dojo.moovies.repository.TheMovieDbRepository
import com.dojo.moovies.ui.home.HomeViewModel
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

val motnApiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(MOTN_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieOfTheNightApi::class.java)
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
    single { get<AppDatabase>().streamingChannelDao() }
}

val repositoryModule = module {
    factory { TheMovieDbRepository(get()) }
    factory { MyListRepository(get()) }
}

val homeViewModel = module {
    viewModel {
        val homeIntercator = HomeIntercator(get(), get())
        HomeViewModel(homeIntercator)
    }
}