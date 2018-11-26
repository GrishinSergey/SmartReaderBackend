package com.sagrishin.smartreader.controllers.impl

import com.sagrishin.smartreader.api.responses.GenreResponse
import com.sagrishin.smartreader.controllers.Controller
import com.sagrishin.smartreader.controllers.Controller.Companion.SUCCESS_STATUS_CODE
import com.sagrishin.smartreader.controllers.GenresController
import com.sagrishin.smartreader.models.impl.GenresModelImpl

class GenresControllerImpl : GenresController {

    private val genresModel: GenresModelImpl

    constructor(genresModel: GenresModelImpl) {
        this.genresModel = genresModel
    }

    override fun getAllGenres(): GenreResponse {
        return GenreResponse.convert(genresModel.getAllGenres(), Controller.SUCCESS_STATUS_CODE)
    }

}
