package com.sagrishin.smartreader.models.repositories

import com.sagrishin.smartreader.core.data.models.DatabaseGenre
import com.sagrishin.smartreader.core.data.database.dao.impl.GenresDaoImpl
import com.sagrishin.smartreader.core.data.repositories.GenresRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class GenresRepositoryImplTest {

    @Test
    fun getAllGenres() {
        val genres = listOf(
                DatabaseGenre(genre = "Genre1"),
                DatabaseGenre(genre = "Genre2"),
                DatabaseGenre(genre = "Genre3"),
                DatabaseGenre(genre = "Genre4"),
                DatabaseGenre(genre = "Genre5"),
                DatabaseGenre(genre = "Genre6")
        )

        val mockedDao = mockk<GenresDaoImpl>()
        every { mockedDao.getAllGenres() } returns genres
        val genresRepository = GenresRepositoryImpl(mockedDao)

        val allGenres = genresRepository.getAllGenres()

        (0 until genres.size).forEach {
            val preparedGenre = genres[it]
            val selectedGenre = allGenres[it]

            Assert.assertEquals(preparedGenre.genre, selectedGenre.genre)
        }
    }

}