package com.sagrishin.smartreader.di.modules

import com.google.gson.Gson
import com.sagrishin.smartreader.controllers.GenresController
import com.sagrishin.smartreader.controllers.impl.GenresControllerImpl
import com.sagrishin.smartreader.core.data.database.SmartReaderDatabase
import com.sagrishin.smartreader.core.data.database.dao.impl.GenresDaoImpl
import com.sagrishin.smartreader.core.threads.Executor
import com.sagrishin.smartreader.models.GenresModel
import com.sagrishin.smartreader.models.impl.GenresRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
@Singleton
class ControllersModule {

    private val db = SmartReaderDatabase.getDatabaseInstance()

    @Provides
    @Singleton
    fun getGenresController(gson: Gson, @Named("OneThreadExecutor") threadExecutor: Executor): GenresController {
        val dao = GenresDaoImpl(db)
        val repo = GenresRepositoryImpl(dao)
        val model = GenresModel(repo, threadExecutor)
        return GenresControllerImpl(model, gson)
    }

}