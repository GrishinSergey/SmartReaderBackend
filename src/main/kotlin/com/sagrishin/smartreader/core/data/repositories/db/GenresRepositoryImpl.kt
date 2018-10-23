package com.sagrishin.smartreader.core.data.repositories.db

import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.models.repositories.models.Genre
import com.sagrishin.smartreader.models.repositories.GenresRepository
import java.lang.ref.WeakReference

class GenresRepositoryImpl : GenresRepository {

    private val dao: GenresDao
    private var genres: WeakReference<List<Genre>>?

    constructor(genresDao: GenresDao) {
        dao = genresDao
        genres = null
    }

    override fun getAllGenres(): List<Genre> {
        if ((null == genres) || (null == genres!!.get())) {
            genres = WeakReference(dao.getAllGenres().map { Genre(it.genre) })
        }
        return genres!!.get()!!
    }

}
