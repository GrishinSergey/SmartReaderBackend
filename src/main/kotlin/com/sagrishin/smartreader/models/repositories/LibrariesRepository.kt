package com.sagrishin.smartreader.models.repositories

import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.Library

interface LibrariesRepository {

    fun getUserLibraries(email: String, start: String, count: Int): List<Library>

    fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): List<Book>

    fun createNewUserLibrary(email: String, library: String): Library

    fun addNewBookToUserLibrary(title: String, email: String, library: String)

    fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int)

    fun deleteBookFromLibrary(title: String, email: String, library: String)

    fun deleteUserLibrary(email: String, library: String)

}
