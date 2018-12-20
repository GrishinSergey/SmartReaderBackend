package com.sagrishin.smartreader.api.responses

import com.sagrishin.smartreader.models.repositories.models.User

data class UserResponse(
        val user: User,
        val status: Int
)

data class AuthResponse(
        val token: String,
        val status: Int
)