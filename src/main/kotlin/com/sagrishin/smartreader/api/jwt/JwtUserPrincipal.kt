package com.sagrishin.smartreader.api.jwt

import io.ktor.auth.Principal

data class JwtUserPrincipal(
        val id: Long,
        val userEmail: String,
        val userName: String
) : Principal