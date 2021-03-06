package com.sagrishin.smartreader.controllers

import com.sagrishin.smartreader.api.responses.AuthResponse
import com.sagrishin.smartreader.api.responses.UserResponse

interface UsersController : Controller {

    fun createNewUser(name: String, email: String): UserResponse

    fun getUserInfoByEmail(email: String): UserResponse

    fun authoriseUser(email: String): AuthResponse

}