package com.dojo.moovies

import android.app.Application
import com.dojo.moovies.core.di.databaseModule
import com.dojo.moovies.core.di.repositoryModule
import com.dojo.moovies.core.di.homeViewModel
import com.dojo.moovies.core.di.motnApiModule
import com.dojo.moovies.core.di.searchViewModel
import com.dojo.moovies.core.di.tmdbApiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MooviesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MooviesApplication)

            modules(listOf(
                tmdbApiModule,
                motnApiModule,
                databaseModule,
                repositoryModule,
                homeViewModel,
                searchViewModel
            ))

        }
    }
}