package com.sagrishin.smartreader.di.modules

import com.sagrishin.smartreader.core.data.database.SmartReaderDatabase
import com.sagrishin.smartreader.core.data.database.dao.BooksDao
import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.dao.LibrariesDao
import com.sagrishin.smartreader.core.data.database.dao.UsersDao
import com.sagrishin.smartreader.core.data.database.dao.impl.BooksDaoImpl
import com.sagrishin.smartreader.core.data.database.dao.impl.GenresDaoImpl
import com.sagrishin.smartreader.core.data.database.dao.impl.LibrariesDaoImpl
import com.sagrishin.smartreader.core.data.database.dao.impl.UsersDaoImpl
import dagger.Module
import dagger.Provides
import org.jetbrains.exposed.sql.Database
import javax.inject.Singleton

@Module
@Singleton
class DaoModule {

    private val db: Database = SmartReaderDatabase.getDatabaseInstance()

    @Provides
    @Singleton
    fun getGenresDao(): GenresDao = GenresDaoImpl(db)

    @Provides
    @Singleton
    fun getBooksDao(genresDao: GenresDao): BooksDao = BooksDaoImpl(db, genresDao)

    @Provides
    @Singleton
    fun getUsersDao(): UsersDao = UsersDaoImpl(db)

    @Provides
    @Singleton
    fun getLibrariesDao(usersDao: UsersDao): LibrariesDao = LibrariesDaoImpl(db, usersDao)

}
