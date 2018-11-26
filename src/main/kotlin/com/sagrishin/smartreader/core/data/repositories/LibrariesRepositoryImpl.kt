package com.sagrishin.smartreader.core.data.repositories

import com.sagrishin.smartreader.core.data.database.dao.LibrariesDao
import com.sagrishin.smartreader.core.data.database.dao.bookDatabaseToEntityConverter
import com.sagrishin.smartreader.models.repositories.LibrariesRepository
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.Genre
import com.sagrishin.smartreader.models.repositories.models.Library

class LibrariesRepositoryImpl : LibrariesRepository {

    private val dao: LibrariesDao

    constructor(dao: LibrariesDao) {
        this.dao = dao
    }

    override fun getUserLibraries(email: String, start: Int, count: Int): List<Library> {
        return dao.getUserLibraries(email, start, count).map { Library(it.libraryName, emptyList()) }
    }

    override fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): List<Book> {
        return dao.getBooksFromUserLibrary(email, library, start, count).books.map {
            Book(it.book.title, Genre(it.book.genre.genre), it.book.author, it.book.rate, it.book.countPages,
                    it.book.pathToCover, it.book.pathToFile)
        }
    }

    override fun createNewUserLibrary(email: String, library: String): Library {
        return Library(dao.createNewUserLibrary(email, library).libraryName, emptyList())
    }

    override fun addNewBookToUserLibrary(title: String, email: String, library: String): Boolean {
        return dao.addNewBookToUserLibrary(title, email, library)
    }

    override fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int): Boolean {
        return dao.updateBookReadingProgress(title, email, library, progress)

    }

    override fun deleteBookFromLibrary(title: String, email: String, library: String): Boolean {
        return dao.deleteBookFromLibrary(title, email, library)
    }

    override fun deleteUserLibrary(email: String, library: String): Boolean {
        return dao.deleteUserLibrary(email, library)
    }

}