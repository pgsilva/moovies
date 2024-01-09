package com.dojo.moovies.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.dojo.moovies.out.api.MovieOfTheNightApi
import com.dojo.moovies.out.api.data.motn.StreamingServicesByCountry
import com.dojo.moovies.out.db.StreamingChannelDao
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.out.db.entity.StreamingChannelEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal object CacheStreamingLoader {

    private lateinit var api: MovieOfTheNightApi

    private lateinit var dao: StreamingChannelDao

    internal fun setup(
        api: () -> MovieOfTheNightApi,
        dao: () -> StreamingChannelDao
    ) {
        this.api = api()
        this.dao = dao()

        GlobalScope.launch {
            loadStreamingDataAsync()
        }
    }

    private suspend fun loadStreamingDataAsync() {
        dao.findAll().collect { cachedData ->
            if (cachedData.isEmpty())
                loadStreamingChannelsFromApi()
        }

    }

    private suspend fun loadStreamingChannelsFromApi() {
        api.getStreamingList().let { response ->
            if (response.isSuccessful) {
                Log.i("Moovies-CacheStreamingLoader", "Streaming Data Loaded")

                response.body()?.let {
                    updateCacheStreamingChannel(dao, it.result.br)
                }
            }
        }
    }

    private suspend fun updateCacheStreamingChannel(
        dao: StreamingChannelDao,
        streamingServices: StreamingServicesByCountry
    ) {
        streamingServices.services.values.forEach {
            val entity = StreamingChannelEntity(
                it.id,
                it.name,
                it.homePage,
                it.themeColorCode,
                it.images.lightThemeImage,
                it.images.darkThemeImage,
                it.images.whiteImage
            )

            dao.update(entity)
        }
        Log.i("Moovies-CacheStreamingLoader", "Streaming Data Saved with successfully")

    }

}