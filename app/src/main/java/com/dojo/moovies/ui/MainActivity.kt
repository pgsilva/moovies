package com.dojo.moovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dojo.moovies.databinding.ActivityMainBinding
import com.dojo.moovies.out.api.MovieOfTheNightApi
import com.dojo.moovies.out.db.StreamingChannelDao
import com.dojo.moovies.ui.home.CacheStreamingLoader
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val api: MovieOfTheNightApi by inject()

    private val dao: StreamingChannelDao by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        supportActionBar?.hide()
        CacheStreamingLoader
            .setup(
                api = { api },
                dao = { dao }
            )
    }

}