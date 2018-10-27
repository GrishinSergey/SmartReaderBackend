package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.*
import com.sagrishin.smartreader.core.data.database.dao.BooksDao
import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.exceptions.DuplicatedDataInDatabaseException
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException
import com.sagrishin.smartreader.core.data.models.DatabaseBook
import com.sagrishin.smartreader.core.data.models.DatabaseGenre
import com.sagrishin.smartreader.core.data.models.DatabaseVoiceOver
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.stream.Collectors

class BooksDaoImpl : BooksDao {

    private val db: Database
    private val genresDao:GenresDao

    constructor(db: Database, genresDao: GenresDao) {
        this.db = db
        this.genresDao = genresDao
    }

    @Throws(NothingFoundInDatabaseException::class)
    override fun getBooksByGenre(genreName: String, start: Int, count: Int): List<DatabaseBook> {
        return transaction (db) { findBooksByGenreName(genreName, start, count) }
    }

    @Throws(NothingFoundInDatabaseException::class, DuplicatedDataInDatabaseException::class)
    override fun getBookByTitleAndAuthor(bookTitle: String, author: String): DatabaseBook {
        return transaction(db) { findBookByAuthorNameAndTitle(author, bookTitle) }
    }

    @Throws(NothingFoundInDatabaseException::class)
    override fun findBooksByEntry(entry: String, genreName: String): List<DatabaseBook> {
        return transaction(db) { getBooksByEntry(genreName, entry) }
    }

    @Throws(NothingFoundInDatabaseException::class)
    override fun getBookVoiceOvers(bookTitle: String, author: String): List<DatabaseVoiceOver> {
        return transaction (db) {
            val book = getBookByTitleAndAuthor(bookTitle, author)
            VoiceOverEntity.find {
                VoiceOvers.book eq book.bookId
            }.toList().parallelStream().map {
                DatabaseVoiceOver(it.pathToVoiceOverFile)
            }.collect(Collectors.toList())
        }
    }

    @Throws(NothingFoundInDatabaseException::class)
    private fun findBooksByGenreName(genreName: String, start: Int, count: Int): List<DatabaseBook> {
        return try {
            val genre = genresDao.findGenreByName(genreName)
            BookEntity.find {
                Books.genre eq genre.genreId
            }.limit(count, start).toList().parallelStream().map {
                bookDatabaseToEntityConverter(it, genre)
            }.collect(Collectors.toList())
        } catch (e: NothingFoundInDatabaseException) {
            throw NothingFoundInDatabaseException("Genre '$genreName' not found. Can't finding books")
        }
    }

    @Throws(NothingFoundInDatabaseException::class)
    private fun getBooksByEntry(genreName: String, entry: String): List<DatabaseBook> {
        return try {
            val genre = genresDao.findGenreByName(genreName)
            BookEntity.find {
                (Books.genre eq genre.genreId) and ((Books.author like entry) and (Books.title like entry))
            }.toList().parallelStream().map {
                bookDatabaseToEntityConverter(it, genre)
            }.collect(Collectors.toList())
        } catch (e: NothingFoundInDatabaseException) {
            throw NothingFoundInDatabaseException("Genre '$genreName' not found. Can't finding books")
        }
    }

    @Throws(NothingFoundInDatabaseException::class, DuplicatedDataInDatabaseException::class)
    private fun findBookByAuthorNameAndTitle(author: String, bookTitle: String): DatabaseBook {
        return try {
            val resultRow = BookEntity.find { (Books.author eq author) and (Books.title eq bookTitle) }.single()
            bookDatabaseToEntityConverter(resultRow, DatabaseGenre())
        } catch (e: IllegalArgumentException) {
            val m = "No one book found with title '$bookTitle' and author '$author'"
            throw NothingFoundInDatabaseException(m)
        } catch (e: NoSuchElementException) {
            val m = "There're several books with title '$bookTitle' and author '$author' in database"
            throw DuplicatedDataInDatabaseException(m)
        }
    }

    private fun bookDatabaseToEntityConverter(resultRow: BookEntity, genre: DatabaseGenre): DatabaseBook {
        return DatabaseBook(
                resultRow.id.value,
                resultRow.title,
                resultRow.author,
                resultRow.pathToCover,
                resultRow.pathToFile,
                resultRow.rate,
                resultRow.countPages,
                genre
        )
    }

}
