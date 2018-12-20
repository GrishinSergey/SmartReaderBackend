package com.sagrishin.smartreader.controllers.impl

import com.sagrishin.smartreader.api.responses.AuthResponse
import com.sagrishin.smartreader.api.responses.UserResponse
import com.sagrishin.smartreader.controllers.Controller
import com.sagrishin.smartreader.controllers.Controller.Companion.SULT
import com.sagrishin.smartreader.controllers.UsersController
import com.sagrishin.smartreader.models.UsersModel
import java.security.MessageDigest
import java.util.*

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

    override fun authoriseUser(email: String): AuthResponse {
        return try {
            val user = usersModel.getUserInfoByEmail(email)
            val encodedUserEmail = Base64.getEncoder().encodeToString(user.userEmail.toByteArray())
            val shaEncoded = MessageDigest.getInstance("SHA-256").digest((encodedUserEmail + SULT).toByteArray())
            val token = shaEncoded.joinToString("") { "%02x".format(it) }
            AuthResponse(token, Controller.SUCCESS_STATUS_CODE)
        } catch (e: Exception) {
            AuthResponse("", Controller.BAD_REQUEST_CODE)
        }
    }

}
