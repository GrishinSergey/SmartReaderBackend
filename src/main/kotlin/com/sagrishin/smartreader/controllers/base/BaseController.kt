package com.sagrishin.smartreader.controllers.base

import com.google.gson.Gson
import com.sagrishin.smartreader.controllers.Controller

abstract class BaseController : Controller {

    companion object {
        const val SUCCESS_STATUS_CODE = 200
    }

    private val gsonConverter: Gson

    constructor(gsonConverter: Gson) {
        this.gsonConverter = gsonConverter
    }

    override fun prepareResponse(response: Any): String {
        return gsonConverter.toJson(response)
    }

}
