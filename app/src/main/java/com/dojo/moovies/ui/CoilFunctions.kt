package com.dojo.moovies.ui

import android.widget.ImageView
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.dojo.moovies.R

private const val TMDB_IMAGE_URL = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2/"

fun ImageView.loadFromTMDBApi(url: String?) {
    this.load("$TMDB_IMAGE_URL$url")
}

fun ImageView.load(url: String? = null) {
    val loader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    load(url, loader) {
//        fallback()
//        error()
        placeholder(R.drawable.img_placeholder_movie_poster)
    }
}