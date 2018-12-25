package com.sagrishin.smartreader.core.data.repositories

import com.sagrishin.smartreader.core.data.database.dao.LibrariesDao
import com.sagrishin.smartreader.core.data.database.dao.bookDatabaseToEntityConverter
import com.sagrishin.smartreader.core.data.models.DatabaseBookInLibrary
import com.sagrishin.smartreader.models.repositories.LibrariesRepository
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.Genre
import com.sagrishin.smartreader.models.repositories.models.Library
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibrariesRepositoryImpl : LibrariesRepository {

    private val dao: LibrariesDao

    @Inject
    constructor(dao: LibrariesDao) {
        this.dao = dao
    }

    override fun getUserLibraries(email: String, start: Int, count: Int): List<Library> {
        return dao.getUserLibraries(email, start, count).map {
            Library(it.libraryName, it.countBooks, it.pathToCover, emptyList())
        }
    }

    override fun isBookRelatesToUserLibrary(email: String, library: String, title: String): Boolean {
        return dao.isBookRelatesToUserLibrary(email, library, title)
    }

    override fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): Library {
        return dao.getBooksFromUserLibrary(email, library, start, count).let {
            Library(it.libraryName,
                    it.countBooks,
                    it.pathToCover,
                    it.books.map(::mapDatabaseBookToRepositoryModel)
            )
        }
    }

    override fun createNewUserLibrary(email: String, library: String): Library {
        return Library(dao.createNewUserLibrary(email, library).libraryName, 0, "", emptyList())
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

    private fun mapDatabaseBookToRepositoryModel(bookEntity: DatabaseBookInLibrary) = Book(
            bookEntity.book.title,
            Genre(bookEntity.book.genre.genre),
            bookEntity.book.author,
            bookEntity.book.rate,
            bookEntity.book.countPages,
            bookEntity.book.pathToCover,
            bookEntity.book.pathToFile
    )

}
