package com.sagrishin.smartreader.controllers

interface Controller {

    companion object {
        const val SUCCESS_STATUS_CODE = 200
        const val BAD_REQUEST_CODE = 400
        const val NOT_FOUND_CODE = 404
        const val SERVER_ERROR_CODE = 500

        const val SULT = "SecurityKey"
    }

}
