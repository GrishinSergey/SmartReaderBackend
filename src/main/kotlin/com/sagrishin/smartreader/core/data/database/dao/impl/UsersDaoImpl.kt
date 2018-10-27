package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.UserEntity
import com.sagrishin.smartreader.core.data.database.Users
import com.sagrishin.smartreader.core.data.database.dao.UsersDao
import com.sagrishin.smartreader.core.data.database.exceptions.DuplicatedDataInDatabaseException
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException
import com.sagrishin.smartreader.core.data.models.DatabaseUser
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class UsersDaoImpl : UsersDao {

    private val db: Database

    constructor(db: Database) {
        this.db = db
    }

    @Throws(DuplicatedDataInDatabaseException::class)
    override fun createNewUser(name: String, email: String): DatabaseUser {
        return transaction (db) { createNewUserWithNameAndEmail(email, name) }
    }

    @Throws(NothingFoundInDatabaseException::class)
    override fun getUserInfoByEmail(email: String): DatabaseUser {
        return transaction (db) { findUserByEmail(email) }
    }

    @Throws(DuplicatedDataInDatabaseException::class)
    private fun createNewUserWithNameAndEmail(email: String, name: String): DatabaseUser {
        return try {
            findUserByEmail(email)
            val m = "user with email '$email' and name '$name' already exists"
            throw DuplicatedDataInDatabaseException(m)
        } catch (e: NothingFoundInDatabaseException) {
            UserEntity.new {
                userEmail = email
                userName = name
            }.let {
                DatabaseUser(it.id.value, it.userName, it.userEmail)
            }
        }
    }

    @Throws(NothingFoundInDatabaseException::class)
    private fun findUserByEmail(email: String): DatabaseUser {
        return try {
            UserEntity.find { Users.userEmail eq email }.single().let {
                DatabaseUser(it.id.value, it.userName, it.userEmail)
            }
        } catch (e: IllegalArgumentException) {
            val m = "There're several users in database with this email:'$email'"
            throw DuplicatedDataInDatabaseException(m)
        } catch (e: NoSuchElementException) {
            val m = "No one user found with email '$email'"
            throw NothingFoundInDatabaseException(m)
        }
    }

}
