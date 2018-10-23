package com.sagrishin.smartreader.core.data.repositories.db

import com.sagrishin.smartreader.core.data.database.dao.BooksDao
import com.sagrishin.smartreader.core.data.database.entities.DatabaseBook
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.VoiceOvers
import com.sagrishin.smartreader.models.repositories.models.Genre
import com.sagrishin.smartreader.models.repositories.BooksRepository
import java.util.stream.Collectors

class BooksRepositoryImpl : BooksRepository {

    private val dao: BooksDao

    constructor(booksDao: BooksDao) {
        dao = booksDao
    }

    override fun getBooksByGenre(genreName: String, start: Int, count: Int): List<Book> {
        return dao.getBooksByGenre(genreName, start, count).parallelStream()
                .map { getRepositoryBookFromEntity(it) }
                .collect(Collectors.toList())
    }

    override fun getBookByTitleAndAuthor(bookTitle: String, author: String): Book {
        val bookEntity = dao.getBookByTitleAndAuthor(bookTitle, author)
        return getRepositoryBookFromEntity(bookEntity)
    }

    override fun findBooksByEntry(entry: String, genreName: String): List<Book> {
        return dao.findBooksByEntry(entry, genreName).parallelStream()
                .map { getRepositoryBookFromEntity(it) }
                .collect(Collectors.toList())
    }

    override fun getBookVoiceOvers(bookTitle: String, author: String): VoiceOvers {
        return VoiceOvers(dao.getBookVoiceOvers(bookTitle, author).parallelStream()
                .map { it.pathToVoiceOverFile }
                .collect(Collectors.toList()))
    }

    private fun getRepositoryBookFromEntity(it: DatabaseBook): Book {
        return Book(
                it.title,
                Genre(it.genre.genre),
                it.author,
                it.rate,
                it.countPages,
                it.pathToCover,
                it.pathToFile
        )
    }

}