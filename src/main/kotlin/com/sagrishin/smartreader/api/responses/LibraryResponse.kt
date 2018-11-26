package com.sagrishin.smartreader.api.responses

import com.sagrishin.smartreader.models.repositories.models.Book

data class LibraryResponse<T>(
        val libraries: T,
        val status: Int
)

data class ResponseLibrary(
        val libraryName: String,
        val books: BooksResponse<List<Book>>
)

data class LibraryActionResponse<T>(
        val action: LibraryAction,
        val result: T,
        val status: Int
)

enum class LibraryAction {
    CREATE,
    ADD_BOOK,
    DELETE_BOOK,
    DELETE_LIBRARY,
    UPDATE_BOOK
}