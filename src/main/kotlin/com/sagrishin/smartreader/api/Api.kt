package com.sagrishin.smartreader.api

import com.google.gson.Gson
import com.sagrishin.smartreader.controllers.BooksController
import com.sagrishin.smartreader.controllers.GenresController
import com.sagrishin.smartreader.controllers.UsersController
import com.sagrishin.smartreader.main.SmartReaderApplication
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.*
import javax.inject.Inject

class Api {

    @Inject
    lateinit var genresController: GenresController
    @Inject
    lateinit var booksController: BooksController
    @Inject
    lateinit var usersController: UsersController

    @Inject
    lateinit var gson: Gson

    init {
        SmartReaderApplication.appComponent.inject(this)
    }

    fun api(): Routing.() -> Unit = {

        get ("/genres.getAllGenres") {
            val allGenres = genresController.getAllGenres()
            call.respondText(gson.toJson(allGenres), ContentType.Application.Json)
        }


        get ("/books.getBooksByGenre/{genre}/{start}/{count}") {
            val genre = call.parameters["genre"]!!
            val start = call.parameters["start"]!!.toInt()
            val count = call.parameters["count"]!!.toInt()
            val books = booksController.getBooksByGenre(genre, start, count)
            call.respondText(gson.toJson(books), ContentType.Application.Json)
        }

        get ("/books.getBookByTitleAndAuthor/{title}/{author}") {
            val genre = call.parameters["genre"]!!
            val author = call.parameters["author"]!!
            val books = booksController.getBookByTitleAndAuthor(genre, author)
            call.respondText(gson.toJson(books), ContentType.Application.Json)
        }

        get ("/books.findBooksByEntry/{entry}/{genre}") {
            val genre = call.parameters["genre"]!!
            val entry = call.parameters["entry"]!!
            val books = booksController.findBooksByEntry(entry, genre)
            call.respondText(gson.toJson(books), ContentType.Application.Json)
        }

        get ("/books.getBookVoiceOvers/{title}/{author}") {
            val genre = call.parameters["genre"]!!
            val author = call.parameters["author"]!!
            val books = booksController.getBookVoiceOver(genre, author)
            call.respondText(gson.toJson(books), ContentType.Application.Json)
        }


        post ("/users.createNewUser/{name}/{email}") {
            val name = call.parameters["name"]!!
            val email = call.parameters["email"]!!
            val newUser = usersController.createNewUser(name, email)
            call.respondText(gson.toJson(newUser), ContentType.Application.Json)
        }

        get ("/users.getUserInfoByEmail/{email}") {
            val email = call.parameters["email"]!!
            val foundUser = usersController.getUserInfoByEmail(email)
            call.respondText(gson.toJson(foundUser), ContentType.Application.Json)
        }


        get ("/libraries.getUserLibraries/{email}/{start}/{count}") {

        }

        get ("/libraries.getBooksFromUserLibrary/{email}/{library}/{start}/{count}") {

        }

        get ("/libraries.createNewUserLibrary/{email}/{library}") {

        }

        post ("/libraries.addNewBookToUserLibrary/{title}/{email}/{library}") {

        }

        patch ("/libraries.updateBookReadingProgress/{title}/{email}/{library}/{progress}") {

        }

        delete ("/libraries.deleteBookFromLibrary/{title}/{email}/{library}") {

        }

        delete ("/libraries.deleteUserLibrary/{email}/{library}") {

        }

    }

}