package com.sagrishin.smartreader.controllers.impl

import com.sagrishin.smartreader.api.responses.UserResponse
import com.sagrishin.smartreader.controllers.Controller
import com.sagrishin.smartreader.controllers.UsersController
import com.sagrishin.smartreader.models.BooksModel
import com.sagrishin.smartreader.models.UsersModel
import com.sagrishin.smartreader.models.repositories.models.User

class UsersControllerImpl : UsersController {

    private val usersModel: UsersModel

    constructor(usersModel: UsersModel) {
        this.usersModel = usersModel
    }

    override fun createNewUser(name: String, email: String): UserResponse {
        return UserResponse(usersModel.createNewUser(name, email), Controller.SUCCESS_STATUS_CODE)
    }

    override fun getUserInfoByEmail(email: String): UserResponse {
        return UserResponse(usersModel.getUserInfoByEmail(email), Controller.SUCCESS_STATUS_CODE)
    }

}
