package com.sagrishin.smartreader.controllers

interface Controller {

    fun prepareResponse(response: Any): String

}
