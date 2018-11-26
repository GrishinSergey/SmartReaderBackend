package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.*
import com.sagrishin.smartreader.core.data.database.dao.BooksDao
import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.dao.LibrariesDao
import com.sagrishin.smartreader.core.data.database.dao.UsersDao
import com.sagrishin.smartreader.core.data.models.DatabaseBook
import com.sagrishin.smartreader.core.data.models.DatabaseGenre
import com.sagrishin.smartreader.core.data.models.DatabaseLibrary
import com.sagrishin.smartreader.core.data.models.DatabaseUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import sun.awt.AWTIcon64_security_icon_yellow32_png
import utils.CircularCollection
import utils.getTestDatabaseInstance

class LibrariesDaoImplTest {

    private lateinit var booksDao: BooksDao
    private lateinit var genresDao: GenresDao
    private lateinit var usersDao: UsersDao
    private lateinit var librariesDao: LibrariesDao

    private val preparedGenres = mutableListOf<DatabaseGenre>()
    private val preparedBooks = mutableListOf<DatabaseBook>()
    private val preparedUsers = mutableListOf<DatabaseUser>()

    private val db = SmartReaderDatabase.getDatabaseInstance()
//    private val db = getTestDatabaseInstance(javaClass.classLoader)

    @Before
    fun setUp() {
        genresDao = GenresDaoImpl(db)
        booksDao = BooksDaoImpl(db, genresDao)
        usersDao = UsersDaoImpl(db)
        librariesDao = LibrariesDaoImpl(db, usersDao)

        transaction (db) {
            SchemaUtils.create(Genres, Books, VoiceOvers, Users, Libraries, UserLibrary, BookLibrary)

            listOf("Genre1", "Genre2", "Genre3").map { genre -> Genres.insert {
                it[Genres.genre] = genre
                it[Genres.link] = ""
            } }
            preparedGenres.addAll(genresDao.getAllGenres())
            val authors = CircularCollection(listOf("Author1", "Author2", "Author3"))
            val genres = CircularCollection(preparedGenres)

            val books = (0 until 10).map { DatabaseBook(
                    title = "Book$it",
                    author = authors.next(),
                    pathToCover = "",
                    pathToFile = "",
                    rate = -1,
                    countPages = -1,
                    genre = genres.next()
            ) }
            books.map { book -> BookEntity.new {
                this.author = book.author
                this.title = book.title
                this.pathToCover = book.pathToCover
                this.pathToFile = book.pathToFile
                this.rate = book.rate
                this.countPages = book.countPages
                this.genre = book.genre.genreId
            } }

            preparedBooks.addAll(BookEntity.all().map { DatabaseBook(
                    bookId = it.id.value,
                    title = it.title,
                    author = it.author,
                    pathToCover = it.pathToCover,
                    pathToFile = it.pathToFile,
                    rate = it.rate,
                    countPages = it.countPages,
                    genre = genres.find { genre -> genre.genreId == it.genre}!!
            ) })

            preparedUsers.addAll((0 until 10).map { "userName$it" to "emailUserName$it@gmail.com" }.map {
                DatabaseUser(-1, it.first, it.second)
            })
            preparedUsers.forEach { usersDao.createNewUser(it.userName, it.userEmail) }
        }
    }

    @After
    fun tearDown() {
        transaction {
            Genres.deleteAll()
            Books.deleteAll()
            VoiceOvers.deleteAll()
            Users.deleteAll()
            Libraries.deleteAll()
        }
    }

    @Test
    fun getUserLibraries() {
        val preparedUser = usersDao.getUserInfoByEmail(preparedUsers[0].userEmail)
        val preparedLibraries = listOf("NewUserLibrary1", "NewUserLibrary2", "NewUserLibrary3")
        transaction (db) {
            val preparedLibraryIds = preparedLibraries.map { LibraryEntity.new { libraryName = it } }
            preparedLibraryIds.forEach { UserLibraryEntity.new {
                user = preparedUser.userId
                library = it.id.value
            } }
        }
        val actualUserLibraries = librariesDao.getUserLibraries(preparedUser.userEmail, 0, 10)

        Assert.assertEquals(3, actualUserLibraries.size)
        (0 until 3).forEach {
            val preparedLibraryName = preparedLibraries[it]
            val actualLibraryName = actualUserLibraries[it].libraryName

            Assert.assertEquals(preparedLibraryName, actualLibraryName)
        }
    }

    @Test
    fun getBooksFromUserLibrary() {
        val preparedUser = usersDao.getUserInfoByEmail(preparedUsers[0].userEmail)
        val preparedLibraryName = "NewUserLibrary1"
        transaction (db) {
            val preparedLibraryEntity = LibraryEntity.new { libraryName = preparedLibraryName }

            UserLibraryEntity.new {
                user = preparedUser.userId
                library = preparedLibraryEntity.id.value
            }

            preparedBooks.forEach { BookLibraryEntity.new {
                this.library = preparedLibraryEntity.id.value
                this.book = it.bookId
                this.progress = 0
            } }
        }

        val actualBooks = librariesDao.getBooksFromUserLibrary(
                preparedUser.userEmail,
                preparedLibraryName,
                0,
                10
        )

        Assert.assertEquals(preparedBooks.size, actualBooks.books.size)
        (0 until 5).forEach {
            val preparedBookName = preparedBooks[it]
            val actualBookName = actualBooks.books[it]

            Assert.assertEquals(preparedBookName.title, actualBookName.book.title)
        }
    }

    @Test
    fun createNewUserLibrary() {
        val preparedUser = preparedUsers[0]
        val preparedLibrary = "NewUserLibrary1"

        val (libraryId, _, _) = librariesDao.createNewUserLibrary(
                preparedUser.userEmail,
                preparedLibrary
        )

        val actualUserLibraries = transaction (db) {
            UserLibraryEntity.find {
                UserLibrary.user eq usersDao.getUserInfoByEmail(preparedUser.userEmail).userId
            }.limit(10, 0).toList().map {
                val library = Libraries.select { Libraries.libraryId eq it.library }.single()
                DatabaseLibrary(library[Libraries.id].value, library[Libraries.libraryName], emptyList())
            }
        }

        Assert.assertNotEquals(libraryId, 0)
        Assert.assertEquals(1, actualUserLibraries.size)
        val actualLibraryName = actualUserLibraries[0].libraryName
        Assert.assertEquals(preparedLibrary, actualLibraryName)
    }

    @Test
    fun addNewBookToUserLibrary() {
//        val preparedUser = preparedUsers[0]
//        val preparedLibrary = "NewUserLibrary1"
//        transaction (db) {
//            val preparedLibraryId = LibraryEntity.new { libraryName = preparedLibrary }.id.value
//            UserLibraryEntity.new {
//                user = usersDao.getUserInfoByEmail(preparedUser.userEmail).userId
//                library = preparedLibraryId
//            }
//        }
//
//        preparedBooks.forEach {
//            librariesDao.addNewBookToUserLibrary(it.title, preparedUser.userEmail, preparedLibrary)
//        }
//
//        val actualBooks = librariesDao.getBooksFromUserLibrary(
//                preparedUser.userEmail,
//                preparedLibrary,
//                0,
//                5
//        )
//        Assert.assertEquals(preparedBooks.size, actualBooks.books.size)
//        (0 until 5).forEach {
//            val preparedBookName = preparedBooks[it]
//            val actualBookName = actualBooks.books[it]
//
//            Assert.assertEquals(preparedBookName, actualBookName)
//        }
    }

    @Test
    fun updateBookReadingProgress() {
//        val preparedUser = preparedUsers[0]
//        val preparedLibrary = "NewUserLibrary1"
//        val preparedLibraryId = transaction (db) {
//            val preparedLibraryId = LibraryEntity.new { libraryName = preparedLibrary }.id.value
//            UserLibraryEntity.new {
//                user = usersDao.getUserInfoByEmail(preparedUser.userEmail).userId
//                library = preparedLibraryId
//            }
//            preparedLibraryId
//        }
//        val preparedBook = preparedBooks[1]
//        BookLibraryEntity.new {
//            this.library = preparedLibraryId
//            this.book = preparedBook.bookId
//            this.progress = 0
//        }
//
//        val updateRes = librariesDao.updateBookReadingProgress(
//                preparedBook.title,
//                preparedUser.userEmail,
//                preparedLibrary,
//                11
//        )
//        val actualBook = librariesDao.getBooksFromUserLibrary(
//                preparedUser.userEmail,
//                preparedLibrary,
//                0,
//                1
//        ).books[0]
//
//        Assert.assertTrue(updateRes)
//        Assert.assertEquals(11, actualBook.progress)
    }

    @Test
    fun deleteBookFromLibrary() {
//        val preparedUser = preparedUsers[0]
//        val preparedLibrary = "NewUserLibrary1"
//        transaction (db) {
//            val preparedLibraryId = LibraryEntity.new { libraryName = preparedLibrary }.id.value
//            UserLibraryEntity.new {
//                user = usersDao.getUserInfoByEmail(preparedUser.userEmail).userId
//                library = preparedLibraryId
//            }
//        }
//
//        preparedBooks.forEach {
//            librariesDao.addNewBookToUserLibrary(it.title, preparedUser.userEmail, preparedLibrary)
//        }
//
//        librariesDao.deleteBookFromLibrary(preparedBooks[0].title, preparedUser.userEmail, preparedLibrary)
//
//        val actualBooks = librariesDao.getBooksFromUserLibrary(
//                preparedUser.userEmail,
//                preparedLibrary,
//                0,
//                5
//        )
//        Assert.assertEquals(4, actualBooks.books.size)
    }

    @Test
    fun deleteUserLibrary() {
        val preparedUser = usersDao.getUserInfoByEmail(preparedUsers[0].userEmail)
        val preparedLibrary = "NewUserLibrary1"
        transaction (db) {
            val preparedLibraryId = LibraryEntity.new { libraryName = preparedLibrary }.id.value
            UserLibraryEntity.new {
                user = preparedUser.userId
                library = preparedLibraryId
            }
        }

        val _count = transaction { UserLibraryEntity.find { UserLibrary.user eq preparedUser.userId }.count() }

        val deleteRes = librariesDao.deleteUserLibrary(preparedUser.userEmail, preparedLibrary)

        Assert.assertTrue(deleteRes)

        val count = transaction { UserLibraryEntity.find { UserLibrary.user eq preparedUser.userId }.count() }

        val userLibraries = librariesDao.getUserLibraries(preparedUser.userEmail, 0, 5)
        Assert.assertEquals(0, userLibraries.size)
    }

}