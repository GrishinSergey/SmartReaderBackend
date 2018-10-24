package com.sagrishin.smartreader.di.modules

import com.google.gson.Gson
import com.sagrishin.smartreader.controllers.BooksController
import com.sagrishin.smartreader.controllers.GenresController
import com.sagrishin.smartreader.controllers.impl.BooksControllerImpl
import com.sagrishin.smartreader.controllers.impl.GenresControllerImpl
import com.sagrishin.smartreader.core.data.database.SmartReaderDatabase
import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.dao.impl.BooksDaoImpl
import com.sagrishin.smartreader.core.data.database.dao.impl.GenresDaoImpl
import com.sagrishin.smartreader.core.data.repositories.BooksRepositoryImpl
import com.sagrishin.smartreader.core.data.repositories.GenresRepositoryImpl
import com.sagrishin.smartreader.core.threads.Executor
import com.sagrishin.smartreader.models.impl.BooksModelImpl
import com.sagrishin.smartreader.models.impl.GenresModelImpl
import dagger.Module
import dagger.Provides
import org.jetbrains.exposed.sql.Database
import javax.inject.Named
import javax.inject.Singleton

@Module
@Singleton
class ControllersModule {

    private val db: Database
    private val genresDao: GenresDao

    init {
        db = SmartReaderDatabase.getDatabaseInstance()
        genresDao = GenresDaoImpl(db)
    }

    @Provides
    @Singleton
    fun getGenresController(gson: Gson, @Named("OneThreadExecutor") threadExecutor: Executor): GenresController {
        val repo = GenresRepositoryImpl(genresDao)
        val model = GenresModelImpl(repo, threadExecutor)
        return GenresControllerImpl(model, gson)
    }

    @Provides
    @Singleton
    fun getBooksController(gson: Gson, @Named("OneThreadExecutor") threadExecutor: Executor): BooksController {
        val dao = BooksDaoImpl(db, genresDao)
        val repo = BooksRepositoryImpl(dao)
        val model = BooksModelImpl(repo, threadExecutor)
        return BooksControllerImpl(model, gson)
    }

}