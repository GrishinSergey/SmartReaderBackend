package com.sagrishin.smartreader.core.data.database.dao.impl

import com.sagrishin.smartreader.core.data.database.Users
import com.sagrishin.smartreader.core.data.database.dao.UsersDao
import com.sagrishin.smartreader.core.data.database.exceptions.DuplicatedDataInDatabaseException
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException
import com.sagrishin.smartreader.core.data.models.DatabaseUser
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.Test
import utils.getTestDatabaseInstance

class UsersDaoImplTest {

    private lateinit var dao: UsersDao

    @Before
    fun setUp() {
        val database = getTestDatabaseInstance(javaClass.classLoader)
        dao = UsersDaoImpl(database)
        transaction (database) { SchemaUtils.create(Users) }
    }

    @After
    fun tearDown() {
        transaction { Users.deleteAll() }
    }

    @Test
    fun createNewUser() {
        val newUser = dao.createNewUser("user1", "user1@gmail.com")
    }

    @Test(expected = DuplicatedDataInDatabaseException::class)
    fun createUserWithExistedEmail() {
        dao.createNewUser("user1", "user1@gmail.com")
        dao.createNewUser("user1", "user1@gmail.com")
    }

    @Test
    fun getUserInfoByEmail() {
        dao.createNewUser("user1", "user1@gmail.com")
        val user = dao.getUserInfoByEmail("user1@gmail.com")
    }

    @Test(expected = NothingFoundInDatabaseException::class)
    fun getUserInfoByNonExistedEmail() {
        dao.getUserInfoByEmail("user11111")
    }

}