package com.sagrishin.smartreader.api

import com.google.gson.Gson
import com.sagrishin.smartreader.controllers.GenresController
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

        }

        get ("/books.getBookByTitleAndAuthor/{title}/{author}") {

        }

        get ("/books.findBooksByEntry/{entry}/{genre}") {

        }

        get ("/books.getBookVoiceOver/{title}") {

        }


        post ("/users.createNewUser/{name}/{email}") {

        }

        get ("/users.getUserInfoByEmail/{email}") {

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