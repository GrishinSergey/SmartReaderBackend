package com.sagrishin.smartreader.core.data.repositories

import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.models.repositories.GenresRepository
import com.sagrishin.smartreader.models.repositories.models.Genre
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresRepositoryImpl : GenresRepository {

    private val dao: GenresDao

    @Inject
    constructor(genresDao: GenresDao) {
        dao = genresDao
    }

    override fun getAllGenres(): List<Genre> {
        return dao.getAllGenres().map { Genre(it.genre) }
    }

}
