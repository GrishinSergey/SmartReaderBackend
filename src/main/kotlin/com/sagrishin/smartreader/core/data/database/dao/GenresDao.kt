package com.sagrishin.smartreader.core.data.database.dao

import com.sagrishin.smartreader.core.data.database.entities.GenreEntity

interface GenresDao {

    fun getAllGenres(): List<GenreEntity>

}