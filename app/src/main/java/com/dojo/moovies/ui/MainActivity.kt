package com.dojo.moovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dojo.moovies.databinding.ActivityMainBinding
import com.dojo.moovies.out.api.MovieOfTheNightApi
import com.dojo.moovies.out.db.MyListDao
import com.dojo.moovies.out.db.StreamingChannelDao
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.ui.home.CacheStreamingLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val api: MovieOfTheNightApi by inject()

    private val streamingChannelDao: StreamingChannelDao by inject()

    private val myListDao: MyListDao by inject()


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
                dao = { streamingChannelDao }
            )


        //TODO remover apos concluir minha lista
        mockMyListInDb()
    }

    private fun mockMyListInDb() {
        val mock = listOf(
            MyListEntity(
                "0566dd09-49a2-4b6b-aa21-33cb42703020",
                2316,
                false,
                "/eDxBkRZRmbzwCxVqY1oSyTKNIid.jpg",
                "The Office",
                "en",
                "The Office",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ipsum.",
                "/7DJKHzAi83BmQrWLrYYOqcoKfhR.jpg",
                "tv",
                8.58,
                3000
            ),
            MyListEntity(
                "a4112dda-ebde-4cc3-a469-c06fff225725",
                569094,
                false,
                "/4HodYYKEIsGOdinkGi2Ucz6X9i0.jpg",
                "Homem-Aranha: Através do Aranhaverso",
                "en",
                "Spider-Man: Across the Spider-Verse",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ipsum.",
                "/fBS6y0LYX4kU6pPSBYMdQy6SIHX.jpg",
                "movie",
                8.58,
                3000
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            mock.forEach { myListDao.update(it) }
        }
    }

}