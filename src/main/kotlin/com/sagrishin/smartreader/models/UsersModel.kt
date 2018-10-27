package com.sagrishin.smartreader.models

import com.sagrishin.smartreader.models.repositories.models.User

interface UsersModel {

    fun createNewUser(name: String, email: String): User

    fun getUserInfoByEmail(email: String): User

}