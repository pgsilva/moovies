package com.dojo.moovies.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dojo.moovies.databinding.ActivityMainBinding
import com.dojo.moovies.out.api.MovieOfTheNightApi
import com.dojo.moovies.out.api.TheMovieDbApi
import com.dojo.moovies.out.api.data.motn.StreamingServicesByCountry
import com.dojo.moovies.out.api.data.motn.StreamingServicesListByCountry
import com.dojo.moovies.out.db.StreamingChannelDao
import com.dojo.moovies.out.db.entity.StreamingChannelEntity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val api: MovieOfTheNightApi by inject()

    private val dao: StreamingChannelDao by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadStreamingDataAsync()
    }

    private fun loadStreamingDataAsync() {
        lifecycleScope.launch {
            api.getStreamingList().let { response ->
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Streaming Data Loaded", Toast.LENGTH_SHORT)
                        .show()

                    response.body()?.let {
                        updateCacheStreamingChannel(it.result.br)
                    }
                }
            }
        }
    }


    private suspend fun updateCacheStreamingChannel(streamingServices: StreamingServicesByCountry) {
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

    }

}