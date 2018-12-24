package com.sagrishin.smartreader.di.modules

import com.sagrishin.smartreader.controllers.BooksController
import com.sagrishin.smartreader.controllers.GenresController
import com.sagrishin.smartreader.controllers.LibrariesController
import com.sagrishin.smartreader.controllers.UsersController
import com.sagrishin.smartreader.controllers.impl.BooksControllerImpl
import com.sagrishin.smartreader.controllers.impl.GenresControllerImpl
import com.sagrishin.smartreader.controllers.impl.LibrariesControllerImpl
import com.sagrishin.smartreader.controllers.impl.UsersControllerImpl
import com.sagrishin.smartreader.core.data.database.dao.BooksDao
import com.sagrishin.smartreader.core.data.database.dao.GenresDao
import com.sagrishin.smartreader.core.data.database.dao.LibrariesDao
import com.sagrishin.smartreader.core.data.database.dao.UsersDao
import com.sagrishin.smartreader.core.data.repositories.BooksRepositoryImpl
import com.sagrishin.smartreader.core.data.repositories.GenresRepositoryImpl
import com.sagrishin.smartreader.core.data.repositories.LibrariesRepositoryImpl
import com.sagrishin.smartreader.core.data.repositories.UsersRepositoryImpl
import com.sagrishin.smartreader.core.threads.Executor
import com.sagrishin.smartreader.models.impl.BooksModelImpl
import com.sagrishin.smartreader.models.impl.GenresModelImpl
import com.sagrishin.smartreader.models.impl.LibrariesModelImpl
import com.sagrishin.smartreader.models.impl.UsersModelImpl
import com.sagrishin.smartreader.models.repositories.BooksRepository
import com.sagrishin.smartreader.models.repositories.GenresRepository
import com.sagrishin.smartreader.models.repositories.LibrariesRepository
import com.sagrishin.smartreader.models.repositories.UsersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
@Singleton
class ControllersModule {

    @Provides
    @Singleton
    fun getGenresController(@Named("OneThreadExecutor") threadExecutor: Executor,
                            repo: GenresRepository): GenresController {
        val model = GenresModelImpl(repo, threadExecutor)
        return GenresControllerImpl(model)
    }

    @Provides
    @Singleton
    fun getBooksController(@Named("OneThreadExecutor") threadExecutor: Executor,
                           repo: BooksRepository): BooksController {
        val model = BooksModelImpl(repo, threadExecutor)
        return BooksControllerImpl(model)
    }

    @Provides
    @Singleton
    fun getUsersController(@Named("OneThreadExecutor") threadExecutor: Executor,
                           repo: UsersRepository): UsersController {
        val model = UsersModelImpl(repo, threadExecutor)
        return UsersControllerImpl(model)
    }

    @Provides
    @Singleton
    fun getLibrariesController(@Named("OneThreadExecutor") threadExecutor: Executor,
                               repo: LibrariesRepository): LibrariesController {
        val model = LibrariesModelImpl(repo, threadExecutor)
        return LibrariesControllerImpl(model)
    }

}