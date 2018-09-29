package com.sagrishin.smartreader.core.data.repositories

import com.sagrishin.smartreader.core.data.repositories.models.Genre

interface GenresRepository {

    fun getAllGenres(): List<Genre>

}
