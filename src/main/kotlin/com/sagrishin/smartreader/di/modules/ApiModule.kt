package com.sagrishin.smartreader.di.modules

import com.google.gson.Gson
import com.sagrishin.smartreader.api.Api
import com.sagrishin.smartreader.controllers.BooksController
import com.sagrishin.smartreader.controllers.GenresController
import com.sagrishin.smartreader.controllers.LibrariesController
import com.sagrishin.smartreader.controllers.UsersController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class ApiModule {

    @Provides
    @Singleton
    fun getApi(genresController: GenresController,
               booksController: BooksController,
               usersController: UsersController,
               librariesController: LibrariesController,
               gson: Gson
    ): Api {
        return Api(genresController, booksController, usersController, librariesController, gson)
    }

}