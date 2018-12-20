package com.sagrishin.smartreader.core.data.database.dao

import com.sagrishin.smartreader.core.data.models.DatabaseUser

interface UsersDao {

    fun createNewUser(name: String, email: String): DatabaseUser

    fun getUserInfoByEmail(email: String): DatabaseUser

    fun getUserById(id: Int): DatabaseUser?

}