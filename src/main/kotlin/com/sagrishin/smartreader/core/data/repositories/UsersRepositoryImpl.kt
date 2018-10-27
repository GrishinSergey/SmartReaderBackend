package com.sagrishin.smartreader.core.data.repositories

import com.sagrishin.smartreader.core.data.database.dao.UsersDao
import com.sagrishin.smartreader.models.repositories.UsersRepository
import com.sagrishin.smartreader.models.repositories.models.User

class UsersRepositoryImpl : UsersRepository {

    private val dao: UsersDao

    constructor(usersDao: UsersDao) {
        dao = usersDao
    }

    override fun createNewUser(name: String, email: String): User {
        val newUser = dao.createNewUser(name, email)
        return User(newUser.userEmail, newUser.userName, emptyList())
    }

    override fun getUserInfoByEmail(email: String): User {
        val foundUser = dao.getUserInfoByEmail(email)
        return User(foundUser.userEmail, foundUser.userName, emptyList())
    }

}