package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.entities.GenreEntity
import com.sagrishin.smartreader.core.data.database.entities.Genres
import com.sagrishin.smartreader.core.data.repositories.models.Genre
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class GenresDaoImpl(db: Database) : GenresDao {

    private val db = db

    override fun getAllGenres(): List<GenreEntity> {
        return transaction (db) { Genres.selectAll().map { GenreEntity(genre = it[Genres.genre]) } }
    }

}
