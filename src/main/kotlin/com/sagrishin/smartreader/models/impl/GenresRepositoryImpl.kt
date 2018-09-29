package com.sagrishin.smartreader.models.impl

import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.repositories.GenresRepository
import com.sagrishin.smartreader.core.data.repositories.models.Genre
import java.lang.ref.WeakReference

class GenresRepositoryImpl() : GenresRepository {

    private lateinit var dao: GenresDao
    private var genres: WeakReference<List<Genre>>? = null

    constructor(genresDao: GenresDao): this() {
        dao = genresDao
    }

    override fun getAllGenres(): List<Genre> {
        if ((null == genres) || (null == genres!!.get())) {
            genres = WeakReference(dao.getAllGenres().map { Genre(it.genre) })
        }
        return genres!!.get()!!
    }

}
