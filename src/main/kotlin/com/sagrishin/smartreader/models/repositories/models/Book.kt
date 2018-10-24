package com.sagrishin.smartreader.models.repositories.models

data class Book(
        val title: String,
        val genreName: Genre,
        val author: String,
        val rate: Int,
        val countPages: Int,
        val pathToImage: String,
        val pathToBookFile: String
)