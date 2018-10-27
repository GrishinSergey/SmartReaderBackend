package com.sagrishin.smartreader.core.data.database.dao

import com.sagrishin.smartreader.core.data.database.exceptions.DuplicatedDataInDatabaseException
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException
import com.sagrishin.smartreader.core.data.models.DatabaseUser

interface UsersDao {

    @Throws(DuplicatedDataInDatabaseException::class)
    fun createNewUser(name: String, email: String): DatabaseUser

    @Throws(NothingFoundInDatabaseException::class)
    fun getUserInfoByEmail(email: String): DatabaseUser

}