package com.dojo.moovies.ui.home

import android.widget.ImageView
import com.dojo.moovies.ui.TmdbImageSize
import com.dojo.moovies.ui.loadFromTMDBApi

object HomeCoverLoader {

    private val COVERS = listOf(
        "8oPHgn8PLJwSYrNjDjUcJeK0s2m.jpg",
        "rBF8wVQN8hTWHspVZBlI3h7HZJ.jpg",
        "/5Iw7zQTHVRBOYpA0V6z0yypOPZh.jpg",
        "/jXJxMcVoEuXzym3vFnjqDW4ifo6.jpg",
        "/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
        "/xGexTKCJDkl12dTW4YCBDXWb1AD.jpg",
        "/3f8aW6vcYRP6mTZdgXm83BiuB9t.jpg",
        "/kVd3a9YeLGkoeR50jGEXM6EqseS.jpg",
        "/9DeGfFIqjph5CBFVQrD6wv9S7rR.jpg",
        "/suaEOtk1N1sgg2MTM7oZd2cfVp3.jpg",
        "/rkB4LyZHo1NHXFEDHl9vSD9r1lI.jpg",
        "/79ytfhiSukl0R4Hg5yLPILTdUXT.jpg"
    )


    fun loadCoverImage(view: ImageView) {
        view.loadFromTMDBApi(COVERS.random(), TmdbImageSize.COVER_SIZE)
    }
}