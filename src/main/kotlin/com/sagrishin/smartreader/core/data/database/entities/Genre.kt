package com.sagrishin.smartreader.core.data.database.entities

import org.jetbrains.exposed.dao.IntIdTable

object Genres : IntIdTable("genres") {

    val genreId = integer("id").autoIncrement().primaryKey()
    val genre = varchar("genre", 255).uniqueIndex()
    val link = varchar("link", 255)

}

data class GenreEntity(val genreId: Int = -1,
                       val genre: String = "",
                       val link: String = "")