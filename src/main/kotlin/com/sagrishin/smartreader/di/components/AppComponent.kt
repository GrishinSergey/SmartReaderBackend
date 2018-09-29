package com.sagrishin.smartreader.di.components

import com.sagrishin.smartreader.api.Api
import com.sagrishin.smartreader.di.modules.ControllersModule
import com.sagrishin.smartreader.di.modules.GsonModule
import com.sagrishin.smartreader.di.modules.ThreadsModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    GsonModule::class,
    ThreadsModule::class,
    ControllersModule::class
])
@Singleton
interface AppComponent {

    fun inject(api: Api)

}