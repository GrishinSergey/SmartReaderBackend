package com.sagrishin.smartreader.core.data.repositories

import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.models.repositories.models.Genre
import com.sagrishin.smartreader.models.repositories.GenresRepository
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

class GenresRepositoryImpl : GenresRepository {

    private val dao: GenresDao

    constructor(genresDao: GenresDao) {
        dao = genresDao
    }

    override fun getAllGenres(): List<Genre> {
        return dao.getAllGenres().map { Genre(it.genre) }
    }

}
