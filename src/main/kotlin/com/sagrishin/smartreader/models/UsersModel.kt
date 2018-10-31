package com.sagrishin.smartreader.models

import com.sagrishin.smartreader.models.exceptions.UserModelException
import com.sagrishin.smartreader.models.repositories.models.User

interface UsersModel {

    @Throws(UserModelException::class)
    fun createNewUser(name: String, email: String): User

    @Throws(UserModelException::class)
    fun getUserInfoByEmail(email: String): User

}