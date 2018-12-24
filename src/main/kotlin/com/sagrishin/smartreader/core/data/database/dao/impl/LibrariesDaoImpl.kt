package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.*
import com.sagrishin.smartreader.core.data.database.dao.BooksDao
import com.sagrishin.smartreader.core.data.database.dao.LibrariesDao
import com.sagrishin.smartreader.core.data.database.dao.UsersDao
import com.sagrishin.smartreader.core.data.database.dao.bookDatabaseToEntityConverter
import com.sagrishin.smartreader.core.data.database.exceptions.DuplicatedDataInDatabaseException
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException
import com.sagrishin.smartreader.core.data.models.*
import com.sagrishin.smartreader.core.threads.impl.ExecutorImpl
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.concurrent.Callable
import java.util.stream.Collectors

class LibrariesDaoImpl : LibrariesDao {

    private val db: Database
    private val usersDao: UsersDao

    constructor(db: Database, usersDao: UsersDao) {
        this.db = db
        this.usersDao = usersDao
    }

    override fun getUserLibraries(email: String, start: Int, count: Int): List<DatabaseLibrary> {
        return transaction(db) {
            UserLibraryEntity.find {
                UserLibrary.user eq usersDao.getUserInfoByEmail(email).userId
            }.limit(count, start).toList().map {
                val library = LibraryEntity.find { Libraries.libraryId eq it.library }.single()
                val countBooks = countBooksInLibrary(library)
                val pathToCover = BookEntity.find {
                    Books.bookId eq BookLibraryEntity.find { BookLibrary.library eq library.id.value }.first().book
                }.first().pathToCover
                DatabaseLibrary(library.id.value, library.libraryName, countBooks, pathToCover, emptyList())
            }
        }
    }

    override fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): DatabaseLibrary {
        return transaction(db) {
            val user = usersDao.getUserInfoByEmail(email)
            try {
                val lib = LibraryEntity.find { Libraries.libraryName eq library }.single()
                findUserLibraryEntityRelation(user, lib)
                val books = findBooksByLibrary(lib, count, start)
                val pathToCover = books[0].book.pathToCover
                val countBooks = countBooksInLibrary(lib)
                return@transaction DatabaseLibrary(lib.id.value, lib.libraryName, countBooks, pathToCover, books)
            } catch (e: IllegalArgumentException) {
                throw DuplicatedDataInDatabaseException(e.message!!)
            } catch (e: NoSuchElementException) {
                throw NothingFoundInDatabaseException(e.message!!)
            }
        }
    }

    override fun createNewUserLibrary(email: String, library: String): DatabaseLibrary {
        return transaction(db) {
            val user = usersDao.getUserInfoByEmail(email)
            val newLibraryEntity = LibraryEntity.new { libraryName = library }
            val newUserLibraryEntity = UserLibrary.insert {
                it[UserLibrary.user] = user.userId
                it[UserLibrary.library] = newLibraryEntity.id.value
            }
            if (newUserLibraryEntity[UserLibrary.id]!!.value > 0) {
                return@transaction DatabaseLibrary(newLibraryEntity.id.value, library)
            } else {
                throw Exception("An error acquired during creating library '$library' for user '$email'")
            }
        }
    }

    override fun addNewBookToUserLibrary(title: String, email: String, library: String): Boolean {
        return transaction(db) {
            val user = usersDao.getUserInfoByEmail(email)
            val book = BookEntity.find { Books.title eq title }.single()
            val lib = LibraryEntity.find { Libraries.libraryName eq library }.single()
            findUserLibraryEntityRelation(user, lib)
            val newBookInLibrary = BookLibraryEntity.new {
                this.book = book.id.value
                this.library = lib.id.value
                this.progress = 0
            }
            newBookInLibrary.id.value > 0
        }
    }

    override fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int): Boolean {
        return transaction(db) {
            val user = usersDao.getUserInfoByEmail(email)
            val book = BookEntity.find { Books.title eq title }.single()
            val lib = LibraryEntity.find { Libraries.libraryName eq library }.single()
            findUserLibraryEntityRelation(user, lib)
            val update = BookLibrary.update({
                (BookLibrary.book eq book.id.value) and (BookLibrary.library eq lib.id.value)
            }) {
                it[this.progress] = progress
            }
            update > 0
        }
    }

    override fun deleteBookFromLibrary(title: String, email: String, library: String): Boolean {
        return transaction(db) {
            val user = usersDao.getUserInfoByEmail(email)
            val book = BookEntity.find { Books.title eq title }.single()
            val lib = LibraryEntity.find { Libraries.libraryName eq library }.single()
            findUserLibraryEntityRelation(user, lib)
            BookLibraryEntity.find {
                (BookLibrary.book eq book.id.value) and (BookLibrary.library eq lib.id.value)
            }.single().delete()
            true
        }
    }

    override fun deleteUserLibrary(email: String, library: String): Boolean {
        return transaction(db) {
            try {
                val user = usersDao.getUserInfoByEmail(email)
                val lib = LibraryEntity.find { Libraries.libraryName eq library }.single()
                findUserLibraryEntityRelation(user, lib)
                lib.delete()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun countBooksInLibrary(library: LibraryEntity): Int {
        return transaction(db) { BookLibraryEntity.find { BookLibrary.library eq library.id.value }.count() }
    }

    private fun findUserLibraryEntityRelation(user: DatabaseUser, lib: LibraryEntity): UserLibraryEntity {
        return UserLibraryEntity.find {
            (UserLibrary.user eq user.userId) and (UserLibrary.library eq lib.id.value)
        }.single()
    }

    private fun findBooksByLibrary(lib: LibraryEntity, count: Int, start: Int): List<DatabaseBookInLibrary> {
        return BookLibraryEntity.find { BookLibrary.library eq lib.id.value }
                .limit(count, start)
                .map(::getBookInLibrary)
    }

    private fun getBookInLibrary(bookLibraryEntity: BookLibraryEntity): DatabaseBookInLibrary {
        return try {
            val resultRowBook = BookEntity.find { Books.bookId eq bookLibraryEntity.book }.single()
            val resultRowGenre = GenreEntity.find { Genres.genreId eq resultRowBook.genre }.single()
            val genre = DatabaseGenre(resultRowGenre.id.value, resultRowGenre.genre, resultRowGenre.link)
            DatabaseBookInLibrary(bookDatabaseToEntityConverter(resultRowBook, genre), bookLibraryEntity.progress)
        } catch (e: IllegalArgumentException) {
            throw DuplicatedDataInDatabaseException(e.message!!)
        } catch (e: NoSuchElementException) {
            throw NothingFoundInDatabaseException(e.message!!)
        }
    }

}