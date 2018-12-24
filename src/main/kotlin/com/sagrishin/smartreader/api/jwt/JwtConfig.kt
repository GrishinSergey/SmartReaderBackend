package com.sagrishin.smartreader.api.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.sagrishin.smartreader.core.data.models.DatabaseUser
import com.sagrishin.smartreader.models.repositories.models.User
import org.jetbrains.exposed.sql.Database
import java.util.*

object JwtConfig {

    private const val secret = "zAP5MBA4B4Ijz0MZaS48"
    private const val issuer = "ktor.io"
    private const val validityInMs = 36_000_00 * 10 // 10 hours
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()

    /**
     * Produce a token for this combination of User and Account
     */
    fun makeToken(user: JwtUserPrincipal): String = JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("userEmail", user.userEmail)
            .withClaim("id", user.id)
            .withExpiresAt(getExpiration())
            .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
