package com.sagrishin.smartreader.core.data.database

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class GenreEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<GenreEntity>(Genres)

    val genre by Genres.genre
    val link by Genres.link

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

class VoiceOverEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<VoiceOverEntity>(VoiceOvers)

    var pathToVoiceOverFile by VoiceOvers.pathToVoiceOverFile
    var book by VoiceOvers.book

}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<UserEntity>(Users)

    var userName by Users.userName
    var userEmail by Users.userEmail

}

class LibraryEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<LibraryEntity>(Libraries)

    var libraryName by Libraries.libraryName

}

class UserLibraryEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<UserLibraryEntity>(UserLibrary)

    var user by UserLibrary.user
    var library by UserLibrary.library

}

class BookLibraryEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<BookLibraryEntity>(BookLibrary)

    var library by BookLibrary.library
    var book by BookLibrary.book
    var progress by BookLibrary.progress

}
