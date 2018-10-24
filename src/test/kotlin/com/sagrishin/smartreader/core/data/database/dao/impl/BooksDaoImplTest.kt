package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.dao.BooksDao
import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.entities.*
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.CircularCollection
import utils.getTestDatabaseInstance

class BooksDaoImplTest {

    private lateinit var booksDao: BooksDao
    private lateinit var genresDao: GenresDao

    private val preparedGenres = mutableListOf<DatabaseGenre>()
    private val preparedBooks = mutableListOf<DatabaseBook>()

    @Before
    fun setUp() {
        val db = getTestDatabaseInstance(javaClass.classLoader)
        genresDao = GenresDaoImpl(db)
        booksDao = BooksDaoImpl(db, genresDao)

        transaction (db) {
            SchemaUtils.create(Genres, Books, VoiceOvers)
            listOf("Genre1", "Genre2", "Genre3").map { genre -> Genres.insert {
                    it[Genres.genre] = genre
                    it[Genres.link] = ""
            } }
            preparedGenres.addAll(genresDao.getAllGenres())
            val authors = CircularCollection(listOf("Author1", "Author2", "Author3"))
            val genres = CircularCollection(preparedGenres)
            preparedBooks.addAll((0 until 10).map { DatabaseBook(
                    title = "Book$it",
                    author = authors.next(),
                    pathToCover = "",
                    pathToFile = "",
                    rate = -1,
                    countPages = -1,
                    genre = genres.next()
            ) })
            preparedBooks.forEach { book -> Books.insert {
                    it[Books.author] = book.author
                    it[Books.title] = book.title
                    it[Books.pathToCover] = book.pathToCover
                    it[Books.pathToFile] = book.pathToFile
                    it[Books.rate] = book.rate
                    it[Books.countPages] = book.countPages
                    it[Books.genre] = book.genre.genreId
            } }

            val bookEntities = BookEntity.all().toList()
            bookEntities.forEach { bookEntity -> (0 until 5).map { VoiceOverEntity.new {
                book = bookEntity.id.value
                pathToVoiceOverFile = "voiceOver$it"
            } } }
        }
    }

    @After
    fun tearDown() {
        transaction {
            Genres.deleteAll()
            Books.deleteAll()
            VoiceOvers.deleteAll()
        }
    }

    @Test
    fun getBooksByGenre() {
        val preparedGenre = "Genre1"
        val preparedBooks = this.preparedBooks.filter { preparedGenre == it.genre.genre }
        val preparedCount = 4

        val selectedBooks = booksDao.getBooksByGenre(preparedGenre, -1, -1)

        Assert.assertEquals(selectedBooks.size, preparedCount)
        (0 until selectedBooks.size).forEach {
            val selectedBook = selectedBooks[it]
            val preparedBook = preparedBooks[it]

            Assert.assertEquals(selectedBook.title, preparedBook.title)
            Assert.assertEquals(selectedBook.author, preparedBook.author)
            Assert.assertEquals(selectedBook.genre.genre, preparedGenre)
        }
    }

    @Test(expected = NothingFoundInDatabaseException::class)
    fun getBooksOfNonExistedGenre() {
        val preparedGenre = "non existed genre"

        booksDao.getBooksByGenre(preparedGenre, -1, -1)
    }

    @Test
    fun getEmptyListOfBooks() {

    }

    @Test
    fun getBookByTitleAndAuthor() {

    }

    @Test
    fun findBooksByEntry() {

    }

    @Test
    fun getBookVoiceOvers() {
        val preparedBook = "Book0"
        val preparedAuthor = "Author1"
        val preparedVoiceOversCount = 5

        val selectedVoiceOvers = booksDao.getBookVoiceOvers(preparedBook, preparedAuthor)

        Assert.assertNotNull(selectedVoiceOvers)
        Assert.assertEquals(preparedVoiceOversCount, selectedVoiceOvers.size)
    }

}
