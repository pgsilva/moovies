package com.dojo.moovies.ui.home

import kotlin.random.Random


object HomeCoverLoader {

    val COVERS = listOf(
        "Akh0M9PMtYjbhGUucozmZ1gaJqt.jpg", //family guy
        "rBF8wVQN8hTWHspVZBlI3h7HZJ.jpg",//rick and morty
        "k6EOrckWFuz7I4z4wiRwz8zsj4H.jpg",//star wars
        "jHxCeXnSchAuwHnmVatTgqMYdX8.jpg",//spiderman
        "xGexTKCJDkl12dTW4YCBDXWb1AD.jpg",//la casa de papel
        "hlAl1isnYiTSobUT9vsmPQWz0Mi.jpg",//how i met your mother
        "v7q8FGi9j07nbjK6gGo0JqMdgmB.jpg",//spiderverse
        "q8eejQcg1bAqImEV8jh8RtBD4uH.jpg",// arcane
        "mLyW3UTgi2lsMdtueYODcfAB9Ku.jpg",//the office
        "a7p3RuCocDAwurgu2bJbAEhJagf.jpg",//lord of the rings
        "5zmiBoMzeeVdQ62no55JOJMY498.jpg",//xogum
        "yXSzo0VU1Q1QaB7Xg5Hqe4tXXA3.jpg",//braeking bad
        //"ncEsesgOJDNrTUED89hYbA117wo.jpg",//the matrix
    )

    private var currentIndex = Random.nextInt(COVERS.size)

    fun getNextCover(): String {
        val cover = COVERS[currentIndex]
        currentIndex = (currentIndex + 1) % COVERS.size
        return cover
    }
    
}