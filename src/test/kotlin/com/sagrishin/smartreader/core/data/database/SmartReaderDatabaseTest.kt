package com.sagrishin.smartreader.core.data.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.sqlite.SQLiteDataSource
import java.sql.Connection

object SmartReaderDatabaseTest {

    const val DATABASE_NAME = "test_smartreader_db.sqlite"

    fun getDatabaseInstance(pathToDatabase: String): Database {
        val db = Database.connect("jdbc:sqlite:$pathToDatabase", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        return db
    }

}
