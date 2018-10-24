package com.sagrishin.smartreader.di.modules

import dagger.Module
import dagger.Provides
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Singleton

@Module
@Singleton
class GsonModule {

    @Provides
    @Singleton
    fun getJsonConverter(): Gson {
        return GsonBuilder().setPrettyPrinting().create()
    }

}
