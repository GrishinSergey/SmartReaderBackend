package com.sagrishin.smartreader.models.repositories

import com.sagrishin.smartreader.models.repositories.models.Genre

interface GenresRepository {

    fun getAllGenres(): List<Genre>

}
