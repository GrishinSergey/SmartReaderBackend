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

    const val CONNECTION_ENCODING = "characterEncoding=utf8"
    const val USE_UNICODE = "useUnicode=true"

    fun getDatabaseInstance(): Database {
        val connect = Database.connect(
                url = getDatabaseServerUrlConnection(),
                driver = DATABASE_DRIVER,
                user = DATABASE_USER,
                password = DATABASE_PASSWORD
        )

        transaction (connect) {
            SchemaUtils.create(Genres)
            SchemaUtils.create(Books)
            SchemaUtils.create(VoiceOvers)
            SchemaUtils.create(Libraries)
            SchemaUtils.create(Users)
            SchemaUtils.create(UserLibrary)
            SchemaUtils.create(BookLibrary)
        }

        return connect
    }

    private fun getDatabaseServerUrlConnection(): String {
        return "jdbc:$DATABASE_TYPE://$DATABASE_HOST:$DATABASE_PORT/$DATABASE_NAME?$CONNECTION_ENCODING&$USE_UNICODE"
    }

}