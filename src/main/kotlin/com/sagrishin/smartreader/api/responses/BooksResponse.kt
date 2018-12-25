package com.sagrishin.smartreader.api.responses

data class BooksResponse<T>(
        val books: T,
        val status: Int
)
