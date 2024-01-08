package com.dojo.moovies.ui

import android.widget.ImageView
import android.os.Build.VERSION.SDK_INT
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.load
import com.dojo.moovies.R

enum class TmdbImageSize {
    POSTER_SIZE, COVER_SIZE, POSTER_BLUR_SIZE
}

private const val TMDB_IMAGE_URL = "https://www.themoviedb.org/t/p"
private const val POSTER_SIZE = "/w300_and_h450_bestv2/"
private const val COVER_SIZE = "/w1920_and_h800_multi_faces/"
private const val POSTER_BLUR_SIZE = "/w300_and_h450_bestv2_filter(blur)/"

fun ImageView.loadFromTMDBApi(url: String, size: TmdbImageSize) {
    when (size) {
        TmdbImageSize.POSTER_SIZE -> this.tryLoad("$TMDB_IMAGE_URL$POSTER_SIZE$url")
        TmdbImageSize.COVER_SIZE -> this.tryLoad("$TMDB_IMAGE_URL$COVER_SIZE$url")
        TmdbImageSize.POSTER_BLUR_SIZE -> this.tryLoad("$TMDB_IMAGE_URL$POSTER_BLUR_SIZE$url")
    }
}


fun ImageView.tryLoad(url: String? = null) {
    val loader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    load(url, loader) {
        fallback(R.drawable.img_no_signal_error)
        error(R.drawable.img_no_signal_error)
        placeholder(R.drawable.img_placeholder_blur)
    }
}

fun ImageView.tryLoadSvg(url: String) {
    val loader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }.build()

    load(url, loader)
}