package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.SmartReaderDatabaseTest
import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.entities.GenreEntity
import com.sagrishin.smartreader.core.data.database.entities.Genres
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

class GenresDaoImplTest {

    private lateinit var genresDao: GenresDao
    private val preparedGenres = listOf("Genre1", "Genre2", "Genre3", "Genre4")

    @Before
    fun setUp() {
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResource("test_smartreader_db.sqlite")
        val databaseFile = File(resource!!.file)
        val db = SmartReaderDatabaseTest.getDatabaseInstance(databaseFile.absolutePath)
        genresDao = GenresDaoImpl(db)

        transaction (db) {
            SchemaUtils.create(Genres)
            preparedGenres.forEach { genre ->
                Genres.insertAndGetId {
                    it[Genres.genre] = genre
                }
            }
        }
    }

    @After
    fun tearDown() {
        transaction { Genres.deleteAll() }
    }

    @Test
    fun getAllGenres() {
        val allGenres = genresDao.getAllGenres().map { it.genre }
        (0 until preparedGenres.size).forEach {
            val preparedGenre = preparedGenres[it]
            val loadedGenre = allGenres[it]
            Assert.assertEquals(loadedGenre, preparedGenre)
        }
    }

}