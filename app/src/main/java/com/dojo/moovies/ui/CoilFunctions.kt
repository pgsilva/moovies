package com.dojo.moovies.ui

import android.widget.ImageView
import coil.load
import com.dojo.moovies.R

enum class TmdbImageSize {
    POSTER_SIZE, COVER_SIZE, POSTER_BLUR_SIZE,POSTER_COVER_SIZE, POSTER_SIZE_DETAIL,
}

private const val TMDB_IMAGE_URL = "https://www.themoviedb.org/t/p"
private const val POSTER_SIZE = "/w300_and_h450_bestv2/"
private const val COVER_SIZE = "/w1920_and_h800_multi_faces/"
private const val POSTER_SIZE_DETAIL = "/w780/"
private const val POSTER_COVER_SIZE = "/original/"
private const val POSTER_BLUR_SIZE = "/w300_and_h450_bestv2_filter(blur)/"

fun ImageView.loadFromTMDBApi(url: String?, size: TmdbImageSize) {
    when (size) {
        TmdbImageSize.POSTER_SIZE -> this.tryLoad("$TMDB_IMAGE_URL$POSTER_SIZE$url")
        TmdbImageSize.COVER_SIZE -> this.tryLoad("$TMDB_IMAGE_URL$COVER_SIZE$url")
        TmdbImageSize.POSTER_BLUR_SIZE -> this.tryLoad("$TMDB_IMAGE_URL$POSTER_BLUR_SIZE$url")
        TmdbImageSize.POSTER_COVER_SIZE ->this.tryLoad("$TMDB_IMAGE_URL$POSTER_COVER_SIZE$url")
        TmdbImageSize.POSTER_SIZE_DETAIL ->this.tryLoad("$TMDB_IMAGE_URL$POSTER_SIZE_DETAIL$url")
    }
}


fun ImageView.tryLoad(url: String? = null) {
    load(url) {
        fallback(R.drawable.img_no_signal_error)
        error(R.drawable.img_no_signal_error)
        placeholder(R.drawable.card_outline)
    }
}
