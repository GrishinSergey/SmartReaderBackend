package com.sagrishin.smartreader.core.data.database.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Genres : IntIdTable("genres") {

    internal val genreId = integer("id")
    val genre = varchar("genre", 255).uniqueIndex()
    val link = varchar("link", 255)

}

class GenreEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<GenreEntity>(Genres)

    val genre by Genres.genre
    val link by Genres.link

}

data class DatabaseGenre(val genreId: Int = -1,
                         val genre: String = "",
                         val link: String = "")