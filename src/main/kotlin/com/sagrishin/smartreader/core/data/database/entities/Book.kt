package com.sagrishin.smartreader.core.data.database.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Books : IntIdTable("books") {

    internal val bookId = integer("id")
    val title = varchar("title", 255).uniqueIndex()
    val author = varchar("author", 255).uniqueIndex()
    val pathToCover = varchar("image", 255).uniqueIndex()
    val pathToFile = varchar("path_to_file", 255).uniqueIndex()
    val rate = integer("rate")
    val countPages = integer("count_pages")
    val genre = integer("genre") references Genres.genreId

}

class BookEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<BookEntity>(Books)

    var title by Books.title
    var author by Books.author
    var pathToCover by Books.pathToCover
    var pathToFile by Books.pathToFile
    var rate by Books.rate
    var countPages by Books.countPages
    var genre by Books.genre

}

data class DatabaseBook(val bookId: Int = -1,
                        val title: String = "",
                        val author: String = "",
                        val pathToCover: String = "",
                        val pathToFile: String = "",
                        val rate: Int = -1,
                        val countPages: Int = -1,
                        val genre: DatabaseGenre = DatabaseGenre())
