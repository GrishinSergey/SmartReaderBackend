package com.sagrishin.smartreader.main.configs

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.HttpMethod

/**
 * This will allow to make Cross-Domain Requests to this server from WEB-clients.
 *
 */
fun Application.installCors() {
    install(CORS) {
        anyHost()
        allowCredentials = true
        listOf(HttpMethod.Get, HttpMethod.Patch, HttpMethod.Put, HttpMethod.Delete).forEach {
            method(it)
        }
    }
}
