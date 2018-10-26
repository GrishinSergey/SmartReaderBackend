package com.sagrishin.smartreader.models.repositories

import com.sagrishin.smartreader.models.repositories.models.User

interface UsersRepository {

    fun createNewUser(name: String, email: String): User

    fun getUserInfoByEmail(email: String): User

}