package com.sagrishin.smartreader.core.data.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


/* Mysql database */
//object SmartReaderDatabase {
//
//    const val DATABASE_NAME = "smartyreader"
//    const val DATABASE_USER = "root"
//    const val DATABASE_PASSWORD = "1111"
//
//    const val DATABASE_DRIVER = "com.mysql.jdbc.Driver"
//    const val DATABASE_TYPE = "mysql"
//
//    const val DATABASE_HOST = "localhost"
//    const val DATABASE_PORT = "3306"
//
//    const val CONNECTION_ENCODING = "characterEncoding=utf8"
//    const val USE_UNICODE = "useUnicode=true"
//
//    fun getDatabaseInstance(): Database {
//        val connect = Database.connect(
//                url = getDatabaseServerUrlConnection(),
//                driver = DATABASE_DRIVER,
//                user = DATABASE_USER,
//                password = DATABASE_PASSWORD
//        )
//
//        transaction (connect) {
//            SchemaUtils.create(Genres)
//            SchemaUtils.create(Books)
//            SchemaUtils.create(VoiceOvers)
//            SchemaUtils.create(Libraries)
//            SchemaUtils.create(Users)
//            SchemaUtils.create(UserLibrary)
//            SchemaUtils.create(BookLibrary)
//        }
//
//        return connect
//    }
//
//    private fun getDatabaseServerUrlConnection(): String {
//        return "jdbc:$DATABASE_TYPE://$DATABASE_HOST:$DATABASE_PORT/$DATABASE_NAME?$CONNECTION_ENCODING&$USE_UNICODE"
//    }
//
//}


/* PostgreSQL database */
object SmartReaderDatabase {

    const val DATABASE_NAME = "d8qnhnjrr13it7"
    const val DATABASE_USER = "wacxgsgswztuey"
    const val DATABASE_PASSWORD = "877b5636020f7e0653596d3efcca9b55914c401c1b8443022e9c296f93b654c0"

    const val DATABASE_DRIVER = "org.postgresql.Driver"
    const val DATABASE_TYPE = "postgresql"

    const val DATABASE_HOST = "ec2-54-225-150-216.compute-1.amazonaws.com"
    const val DATABASE_PORT = "5432"

    const val CONNECTION_ENCODING = "characterEncoding=utf8"
    const val USE_UNICODE = "useUnicode=true"
    const val SSL_PARAM = "ssl=true"
    const val SSL_FACTORY = "sslfactory=org.postgresql.ssl.NonValidatingFactory"

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
        return "jdbc:$DATABASE_TYPE://$DATABASE_HOST:$DATABASE_PORT/$DATABASE_NAME?${getParams()}"
    }

    private fun getParams(): String {
        return "$CONNECTION_ENCODING&$USE_UNICODE&$SSL_PARAM&$SSL_FACTORY"
    }

}
