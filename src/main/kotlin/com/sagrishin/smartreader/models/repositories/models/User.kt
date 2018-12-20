package com.sagrishin.smartreader.models.repositories.models

data class User(
        val id: Long,
        val userEmail: String,
        val userName: String,
        val libraries: List<Library>
)