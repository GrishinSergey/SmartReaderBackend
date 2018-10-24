package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.entities.DatabaseGenre
import com.sagrishin.smartreader.core.data.database.entities.GenreEntity
import com.sagrishin.smartreader.core.data.database.entities.Genres
import com.sagrishin.smartreader.core.data.database.exceptions.DuplicatedDataInDatabaseException
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.stream.Collectors

class GenresDaoImpl : GenresDao {

    private val db: Database

    constructor(db: Database) {
        this.db = db
    }

    override fun getAllGenres(): List<DatabaseGenre> {
        return transaction (db) { GenreEntity.all().toList()
                .parallelStream()
                .map { DatabaseGenre(it.id.value, it.genre, it.link) }
                .collect(Collectors.toList())
        }
    }

    @Throws(NothingFoundInDatabaseException::class)
    override fun findGenreByName(genreName: String): DatabaseGenre {
        return transaction(db) { getGenreByName(genreName) }
    }

    @Throws(NothingFoundInDatabaseException::class)
    private fun getGenreByName(genreName: String): DatabaseGenre {
        return try {
            val foundGenre = GenreEntity.find { Genres.genre eq genreName }.single()
            DatabaseGenre(foundGenre.id.value, foundGenre.genre, foundGenre.link)
        } catch (e: Exception) {
            throw NothingFoundInDatabaseException("no one genre found by '$genreName'")
        } catch (e: NoSuchElementException) {
            val m = "Duplicates of genre with name '$genreName'"
            throw DuplicatedDataInDatabaseException(m)
        }
    }

}
