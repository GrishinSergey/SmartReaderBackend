package com.sagrishin.smartreader.core.data.database.dao

import com.sagrishin.smartreader.core.data.models.DatabaseGenre

interface GenresDao {

    fun getAllGenres(): List<DatabaseGenre>

    fun findGenreByName(genreName: String): DatabaseGenre

}