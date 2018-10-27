package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.Genres
import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.getTestDatabaseInstance

class GenresDaoImplTest {

    private lateinit var genresDao: GenresDao
    private val preparedGenres = listOf("Genre1", "Genre2", "Genre3", "Genre4")

    @Before
    fun setUp() {
        val db = getTestDatabaseInstance(javaClass.classLoader)
        genresDao = GenresDaoImpl(db)

        transaction (db) {
            SchemaUtils.create(Genres)
            preparedGenres.forEach { genre -> Genres.insert {
                it[Genres.genre] = genre
                it[Genres.link] = "link"
            } }
        }
    }

    @After
    fun tearDown() {
        transaction { Genres.deleteAll() }
    }

    @Test
    fun getAllGenres() {
        val allGenres = genresDao.getAllGenres()
        (0 until preparedGenres.size).forEach {
            val preparedGenre = preparedGenres[it]
            val loadedGenre = allGenres[it]
            Assert.assertNotEquals(loadedGenre.genreId, -1)
            Assert.assertEquals(loadedGenre.genre, preparedGenre)
        }
    }

    @Test
    fun findGenreByName() {
        val findGenreWithName = genresDao.findGenreByName("Genre1")
        Assert.assertNotEquals(findGenreWithName.genreId, -1)
        Assert.assertEquals("Genre1", findGenreWithName.genre)
    }

    @Test(expected = NothingFoundInDatabaseException::class)
    fun findNonExistedGenre() {
        genresDao.findGenreByName("Genre111111111")
    }

}