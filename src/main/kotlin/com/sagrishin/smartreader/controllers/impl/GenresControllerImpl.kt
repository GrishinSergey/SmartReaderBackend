package com.sagrishin.smartreader.controllers.impl

import com.google.gson.Gson
import com.sagrishin.smartreader.api.responses.GenreResponse
import com.sagrishin.smartreader.controllers.GenresController
import com.sagrishin.smartreader.controllers.base.BaseController
import com.sagrishin.smartreader.models.impl.GenresModelImpl

class GenresControllerImpl : BaseController, GenresController {

    private val genresModel: GenresModelImpl

    constructor(genresModel: GenresModelImpl, gson: Gson) : super(gson) {
        this.genresModel = genresModel
    }

    override fun getAllGenres(): GenreResponse {
        return GenreResponse.convert(genresModel.getAllGenres(), SUCCESS_STATUS_CODE)
    }

}
