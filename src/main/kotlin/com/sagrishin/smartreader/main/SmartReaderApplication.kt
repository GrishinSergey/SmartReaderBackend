package com.sagrishin.smartreader.main

import com.sagrishin.smartreader.api.jwt.JwtConfig
import com.sagrishin.smartreader.di.components.AppComponent
import com.sagrishin.smartreader.di.components.DaggerAppComponent
import com.sagrishin.smartreader.di.modules.*
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ConditionalHeaders
import io.ktor.features.DefaultHeaders
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    val appComponent: AppComponent = DaggerAppComponent.builder()
            .controllersModule(ControllersModule())
            .gsonModule(GsonModule())
            .daoModule(DaoModule())
            .threadsModule(ThreadsModule())
            .apiModule(ApiModule())
            .build()
    val usersRepository = appComponent.getUsersRepository()
    val api = appComponent.getApi()
    val port = System.getenv("PORT")?.toInt() ?: 8080

    embeddedServer(Netty, port) {
        install(DefaultHeaders)
        install(ConditionalHeaders)
        install(CallLogging)

        install(Authentication) {
            jwt("jwt") {
                verifier(JwtConfig.verifier)
                realm = "ktor.io"
                validate { it.payload.getClaim("id").asInt()?.let(usersRepository::getUserInfoForTokenAuth) }
            }
        }

        routing(api.api())
    }.start(true)
}
