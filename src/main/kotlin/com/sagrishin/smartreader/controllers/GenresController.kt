package com.sagrishin.smartreader.controllers

import com.sagrishin.smartreader.api.responses.GenreResponse

interface GenresController : Controller {

    fun getAllGenres(): GenreResponse

}
