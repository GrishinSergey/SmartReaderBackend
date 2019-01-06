package com.sagrishin.smartreader.core.data.database.dao

import com.sagrishin.smartreader.core.data.models.DatabaseLibrary

interface LibrariesDao {

    fun getUserLibraries(email: String, start: Int, count: Int): List<DatabaseLibrary>

    fun getAllUserLibraries(email: String): List<DatabaseLibrary>

    fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): DatabaseLibrary

    fun createNewUserLibrary(email: String, library: String): DatabaseLibrary

    fun addNewBookToUserLibrary(title: String, email: String, library: String): Boolean

    fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int): Boolean

    fun deleteBookFromLibrary(title: String, email: String, library: String): Boolean

    fun deleteUserLibrary(email: String, library: String): Boolean

    fun isBookRelatesToUserLibrary(email: String, library: String, title: String): Boolean

    fun isBookFavoritesByUser(email: String, title: String): Boolean

}
