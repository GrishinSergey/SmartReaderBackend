package com.sagrishin.smartreader.models.impl

import com.sagrishin.smartreader.core.threads.Executor
import com.sagrishin.smartreader.models.UsersModel
import com.sagrishin.smartreader.models.repositories.UsersRepository
import com.sagrishin.smartreader.models.repositories.models.User

class UsersModelImpl : UsersModel {

    private val usersRepository: UsersRepository
    private val executor: Executor

    constructor(usersRepository: UsersRepository, executor: Executor) {
        this.usersRepository = usersRepository
        this.executor = executor
    }

    override fun createNewUser(name: String, email: String): User {
        return usersRepository.createNewUser(name, email)
    }

    override fun getUserInfoByEmail(email: String): User {
        return usersRepository.getUserInfoByEmail(email)
    }

}
