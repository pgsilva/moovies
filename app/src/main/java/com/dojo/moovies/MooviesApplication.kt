package com.dojo.moovies

import android.app.Application
import com.dojo.moovies.core.di.apiModule
import com.dojo.moovies.core.di.databaseModule
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
                apiModule,
                databaseModule
            ))

        }
    }
}