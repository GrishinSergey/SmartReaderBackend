package com.sagrishin.smartreader.di.modules

import com.sagrishin.smartreader.core.data.repositories.BooksRepositoryImpl
import com.sagrishin.smartreader.core.data.repositories.GenresRepositoryImpl
import com.sagrishin.smartreader.core.data.repositories.LibrariesRepositoryImpl
import com.sagrishin.smartreader.core.data.repositories.UsersRepositoryImpl
import com.sagrishin.smartreader.models.repositories.BooksRepository
import com.sagrishin.smartreader.models.repositories.GenresRepository
import com.sagrishin.smartreader.models.repositories.LibrariesRepository
import com.sagrishin.smartreader.models.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
@Singleton
abstract class RepositoriesModue {

    @Binds
    @Singleton
    abstract fun getGenresRepository(repository: GenresRepositoryImpl): GenresRepository

    @Binds
    @Singleton
    abstract fun getBooksRepository(repository: BooksRepositoryImpl): BooksRepository

    @Binds
    @Singleton
    abstract fun getUsersRepository(repository: UsersRepositoryImpl): UsersRepository

    @Binds
    @Singleton
    abstract fun getLibrariesRepository(repository: LibrariesRepositoryImpl): LibrariesRepository

}