package com.sagrishin.smartreader.main

import com.sagrishin.smartreader.api.jwt.JwtConfig
import com.sagrishin.smartreader.di.components.AppComponent
import com.sagrishin.smartreader.di.components.DaggerAppComponent
import com.sagrishin.smartreader.di.modules.*
import com.sagrishin.smartreader.main.configs.installCors
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ConditionalHeaders
import io.ktor.features.DefaultHeaders
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object SmartReaderApplication {

    private val appComponent: AppComponent = DaggerAppComponent.builder()
            .controllersModule(ControllersModule())
            .gsonModule(GsonModule())
            .daoModule(DaoModule())
            .threadsModule(ThreadsModule())
            .apiModule(ApiModule())
            .build()

    @JvmStatic
    fun main(args: Array<String>) {
        val usersDao = appComponent.getUsersDao()
        val api = appComponent.getApi()

        embeddedServer(Netty, 8080) {
            install(DefaultHeaders)
            install(ConditionalHeaders)
            install(CallLogging)
            installCors()

            install(Authentication) {
                jwt("jwt") {
                    verifier(JwtConfig.verifier)
                    realm = "ktor.io"
                    validate { it.payload.getClaim("id").asInt()?.let(usersDao::getUserById) }
                }
            }

            routing(api.api())
        }.start(true)
    }

}
