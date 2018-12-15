package com.sagrishin.smartreader.api

import com.google.gson.Gson
import com.sagrishin.smartreader.controllers.BooksController
import com.sagrishin.smartreader.controllers.GenresController
import com.sagrishin.smartreader.controllers.LibrariesController
import com.sagrishin.smartreader.controllers.UsersController
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import java.net.URLDecoder

class Api {

    private val genresController: GenresController
    private val booksController: BooksController
    private val usersController: UsersController
    private val librariesController: LibrariesController

    private val gson: Gson

    constructor(genresController: GenresController,
                booksController: BooksController,
                usersController: UsersController,
                librariesController: LibrariesController,
                gson: Gson
    ) {
        this.genresController = genresController
        this.booksController = booksController
        this.usersController = usersController
        this.librariesController = librariesController
        this.gson = gson
    }

    fun api(): Routing.() -> Unit = {

        get ("/genres.getAllGenres") {
//            val auth = call.request.headers["Authorization"]
//            call.respond(HttpStatusCode.PaymentRequired, )

            val allGenres = genresController.getAllGenres()
            call.respondText(gson.toJson(allGenres), ContentType.Application.Json)
        }


        get ("/books.getBooksByGenre/{genre}/{start}/{count}") {
            val genre = URLDecoder.decode(call.parameters["genre"]!!, "UTF-8")
            val start = call.parameters["start"]!!.toInt()
            val count = call.parameters["count"]!!.toInt()
            val books = booksController.getBooksByGenre(genre, start, count)
            call.respondText(gson.toJson(books), ContentType.Application.Json)
        }

        get ("/books.getBookByTitleAndAuthor/{title}/{author}") {
            val title = call.parameters["title"]!!
            val author = call.parameters["author"]!!
            val books = booksController.getBookByTitleAndAuthor(title, author)
            call.respondText(gson.toJson(books), ContentType.Application.Json)
        }

        get ("/books.findBooksByEntry/{entry}/{genre}") {
            val entry = call.parameters["entry"]!!
            val genre = call.parameters["genre"]!!
            val books = booksController.findBooksByEntry(entry, genre)
            call.respondText(gson.toJson(books), ContentType.Application.Json)
        }

        get ("/books.getBookVoiceOvers/{title}/{author}") {
            val title = call.parameters["title"]!!
            val author = call.parameters["author"]!!
            val books = booksController.getBookVoiceOver(title, author)
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
            val email = call.parameters["email"]!!
            val start = call.parameters["start"]!!.toInt()
            val count = call.parameters["count"]!!.toInt()
            val userLibraries = librariesController.getUserLibraries(email, start, count)
            call.respondText(gson.toJson(userLibraries), ContentType.Application.Json)
        }

        get ("/libraries.getBooksFromUserLibrary/{email}/{library}/{start}/{count}") {
            val email = call.parameters["email"]!!
            val library = call.parameters["library"]!!
            val start = call.parameters["start"]!!.toInt()
            val count = call.parameters["count"]!!.toInt()
            val books = librariesController.getBooksFromUserLibrary(email, library, start, count)
            call.respondText(gson.toJson(books), ContentType.Application.Json)
        }

        post ("/libraries.createNewUserLibrary/{email}/{library}") {
            val email = call.parameters["email"]!!
            val library = call.parameters["library"]!!
            val newLibrary = librariesController.createNewUserLibrary(email, library)
            call.respondText(gson.toJson(newLibrary), ContentType.Application.Json)
        }

        post ("/libraries.addNewBookToUserLibrary/{title}/{email}/{library}") {
            val title = call.parameters["title"]!!
            val email = call.parameters["email"]!!
            val library = call.parameters["library"]!!
            val newBook = librariesController.addNewBookToUserLibrary(title, email, library)
            call.respondText(gson.toJson(newBook), ContentType.Application.Json)
        }

        patch ("/libraries.updateBookReadingProgress/{title}/{email}/{library}/{progress}") {
            val title = call.parameters["title"]!!
            val email = call.parameters["email"]!!
            val library = call.parameters["library"]!!
            val progress = call.parameters["progress"]!!.toInt()
            val progressResult = librariesController.updateBookReadingProgress(title, email, library, progress)
            call.respondText(gson.toJson(progressResult), ContentType.Application.Json)
        }

        delete ("/libraries.deleteBookFromLibrary/{title}/{email}/{library}") {
            val title = call.parameters["title"]!!
            val email = call.parameters["email"]!!
            val library = call.parameters["library"]!!
            val deletingResult = librariesController.deleteBookFromLibrary(title, email, library)
            call.respondText(gson.toJson(deletingResult), ContentType.Application.Json)

        }

        delete ("/libraries.deleteUserLibrary/{email}/{library}") {
            val email = call.parameters["email"]!!
            val library = call.parameters["library"]!!
            val deletingResult = librariesController.deleteUserLibrary(email, library)
            call.respondText(gson.toJson(deletingResult), ContentType.Application.Json)
        }

    }

}
