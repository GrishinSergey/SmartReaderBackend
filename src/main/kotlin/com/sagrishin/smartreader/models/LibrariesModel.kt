package com.sagrishin.smartreader.models

import com.sagrishin.smartreader.models.exceptions.LibraryModelException
import com.sagrishin.smartreader.models.exceptions.UserModelException
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.Library

interface LibrariesModel {

    @Throws(LibraryModelException::class)
    fun getUserLibraries(email: String, start: Int, count: Int): List<Library>

    @Throws(LibraryModelException::class)
    fun getUserLibraries(email: String): List<Library>

    @Throws(LibraryModelException::class)
    fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): Library

    @Throws(LibraryModelException::class)
    fun createNewUserLibrary(email: String, library: String): Library

    fun addNewBookToUserLibrary(title: String, email: String, library: String): Boolean

    fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int): Boolean

    fun deleteBookFromLibrary(title: String, email: String, library: String): Boolean

    fun deleteUserLibrary(email: String, library: String): Boolean

    fun isBookRelatesToUserLibrary(email: String, library: String, title: String): Boolean

    fun isBookFavoritesByUser(email: String, title: String): Boolean

}
