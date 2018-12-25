package com.sagrishin.smartreader.controllers

import com.sagrishin.smartreader.api.responses.LibraryActionResponse
import com.sagrishin.smartreader.api.responses.LibraryResponse
import com.sagrishin.smartreader.api.responses.ResponseLibrary

interface LibrariesController {

    fun getUserLibraries(email: String, start: Int, count: Int): LibraryResponse<List<ResponseLibrary>>

    fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): ResponseLibrary

    fun createNewUserLibrary(email: String, library: String): LibraryActionResponse<Boolean>

    fun addNewBookToUserLibrary(title: String, email: String, library: String): LibraryActionResponse<Boolean>

    fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int):
            LibraryActionResponse<Boolean>

    fun deleteBookFromLibrary(title: String, email: String, library: String): LibraryActionResponse<Boolean>

    fun deleteUserLibrary(email: String, library: String): LibraryActionResponse<Boolean>

    fun isBookRelatesToUserLibrary(email: String, library: String, title: String): Boolean

}
