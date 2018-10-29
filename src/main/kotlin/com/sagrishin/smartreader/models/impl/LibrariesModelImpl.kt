package com.sagrishin.smartreader.models.impl

import com.sagrishin.smartreader.models.LibrariesModel
import com.sagrishin.smartreader.models.exceptions.LibraryModelException
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.Library

class LibrariesModelImpl : LibrariesModel {

    @Throws(LibraryModelException::class)
    override fun getUserLibraries(email: String, start: String, count: Int): List<Library> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Throws(LibraryModelException::class)
    override fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): List<Book> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Throws(LibraryModelException::class)
    override fun createNewUserLibrary(email: String, library: String): Library {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addNewBookToUserLibrary(title: String, email: String, library: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteBookFromLibrary(title: String, email: String, library: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUserLibrary(email: String, library: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}