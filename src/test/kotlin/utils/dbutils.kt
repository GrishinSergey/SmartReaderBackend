package utils

import com.sagrishin.smartreader.core.data.database.SmartReaderDatabaseTest
import org.jetbrains.exposed.sql.Database
import java.io.File

fun getTestDatabaseInstance(classLoader: ClassLoader): Database {
    val resource = classLoader.getResource(SmartReaderDatabaseTest.DATABASE_NAME)
    val databaseFile = File(resource!!.file)
    return SmartReaderDatabaseTest.getDatabaseInstance(databaseFile.absolutePath)
}