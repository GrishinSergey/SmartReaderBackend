package com.sagrishin.smartreader.core.data.database

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Genres : IntIdTable("genres") {

    internal val genreId = integer("id")
    val genre = varchar("genre", 255).uniqueIndex()
    val link = varchar("link", 255)

}

object Books : IntIdTable("books") {

    internal val bookId = integer("id")
    val title = varchar("title", 255).uniqueIndex()
    val author = varchar("author", 255).uniqueIndex()
    val pathToCover = varchar("image", 255).uniqueIndex()
    val pathToFile = varchar("path_to_file", 255).uniqueIndex()
    val rate = integer("rate")
    val countPages = integer("count_pages")
    val genre = integer("genre").references(Genres.genreId, ReferenceOption.CASCADE, ReferenceOption.CASCADE)

}

object VoiceOvers : IntIdTable("voice_overs") {

    val pathToVoiceOverFile = varchar("path_to_voice_over_file", 255)
    val book = integer("book").references(Books.bookId, ReferenceOption.CASCADE, ReferenceOption.CASCADE)

}

object Libraries : IntIdTable("libraries") {

    internal val libraryId = integer("id")
    val libraryName = varchar("library_name", 255).uniqueIndex()

}

object Users : IntIdTable("users") {

    internal val userId = integer("id")
    val userName = varchar("user_name", 255).uniqueIndex()
    val userEmail = varchar("user_email", 255).uniqueIndex()

}

object UserLibrary : IntIdTable("users_libraries") {

    val library = integer("library")
            .references(Libraries.libraryId, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val user = integer("user").references(Users.userId, ReferenceOption.CASCADE, ReferenceOption.CASCADE)

}

object BookLibrary : IntIdTable("books_libraries") {

    val library = integer("library")
            .references(Libraries.libraryId, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val book = integer("book").references(Books.bookId, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val progress = integer("progress")

}
