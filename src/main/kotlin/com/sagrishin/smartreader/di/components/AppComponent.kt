package com.sagrishin.smartreader.di.components

import com.sagrishin.smartreader.api.Api
import com.sagrishin.smartreader.di.modules.*
import com.sagrishin.smartreader.models.repositories.UsersRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    GsonModule::class,
    ThreadsModule::class,
    DaoModule::class,
    RepositoriesModue::class,
    ControllersModule::class,
    ApiModule::class
])
@Singleton
interface AppComponent {

    fun getApi(): Api

    fun getUsersRepository(): UsersRepository

}