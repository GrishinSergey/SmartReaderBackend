package com.sagrishin.smartreader.models

import com.sagrishin.smartreader.models.repositories.models.Genre

interface GenresModel {

    fun getAllGenres(): List<Genre>

}