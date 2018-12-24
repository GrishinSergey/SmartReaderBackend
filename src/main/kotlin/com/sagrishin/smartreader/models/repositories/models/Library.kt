package com.sagrishin.smartreader.models.repositories.models

data class Library(
        val libraryName: String,
        val countBooks: Int,
        val pathToCover: String,
        val books: List<Book>
)
