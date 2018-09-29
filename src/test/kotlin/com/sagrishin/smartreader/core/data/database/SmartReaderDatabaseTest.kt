package com.sagrishin.smartreader.core.data.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.sqlite.SQLiteDataSource
import java.sql.Connection

object SmartReaderDatabaseTest {

    fun getDatabaseInstance(pathToDatabase: String): Database {
        val ds = SQLiteDataSource()
        ds.url = "jdbc:sqlite:$pathToDatabase"
        val db = Database.connect(ds)
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        return db
    }

}
