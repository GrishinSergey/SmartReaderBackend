package com.sagrishin.smartreader.models.repositories

import com.sagrishin.smartreader.models.repositories.models.Library

interface LibrariesRepository {

    fun getUserLibraries(email: String, start: Int, count: Int): List<Library>

    fun getAllUserLibraries(email: String): List<Library>

    fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): Library

    fun createNewUserLibrary(email: String, library: String): Library

    fun addNewBookToUserLibrary(title: String, email: String, library: String): Boolean

    fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int): Boolean

    fun deleteBookFromLibrary(title: String, email: String, library: String): Boolean

    fun deleteUserLibrary(email: String, library: String): Boolean

    fun isBookRelatesToUserLibrary(email: String, library: String, title: String): Boolean

    fun isBookFavoritesByUser(email: String, title: String): Boolean

}
