package com.sagrishin.smartreader.main

import com.sagrishin.smartreader.api.Api
import com.sagrishin.smartreader.di.components.AppComponent
import com.sagrishin.smartreader.di.components.DaggerAppComponent
import com.sagrishin.smartreader.di.modules.ControllersModule
import com.sagrishin.smartreader.di.modules.GsonModule
import com.sagrishin.smartreader.di.modules.ThreadsModule

import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ConditionalHeaders
import io.ktor.features.DefaultHeaders
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object SmartReaderApplication {

    val appComponent: AppComponent = DaggerAppComponent.builder()
            .controllersModule(ControllersModule())
            .gsonModule(GsonModule())
            .threadsModule(ThreadsModule())
            .build()

    @JvmStatic
    fun main(args: Array<String>) {
        embeddedServer(Netty, 8080) {
            install(DefaultHeaders)
            install(ConditionalHeaders)
            install(CallLogging)
            routing(Api().api())
        }.start(true)
    }

}
