package com.sagrishin.smartreader.core.data.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object SmartReaderDatabase {

    const val DATABASE_NAME = "smartyreader"
    const val DATABASE_USER = "root"
    const val DATABASE_PASSWORD = "1111"
    const val DATABASE_DRIVER = "com.mysql.jdbc.Driver"
    const val DATABASE_TYPE = "mysql"
    const val DATABASE_HOST = "localhost"
    const val DATABASE_PORT = "3306"

    fun getDatabaseInstance(): Database {
        val connect = Database.connect(
                url = "jdbc:$DATABASE_TYPE://$DATABASE_HOST:$DATABASE_PORT/$DATABASE_NAME",
                driver = DATABASE_DRIVER,
                user = DATABASE_USER,
                password = DATABASE_PASSWORD
        )

        transaction (connect) {
            SchemaUtils.create(Genres, Books, VoiceOvers, Libraries, Users, UserLibrary, BookLibrary)
        }

        return connect
    }

}