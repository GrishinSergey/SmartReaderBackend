package com.sagrishin.smartreader.models.impl

import com.sagrishin.smartreader.core.threads.Executor
import com.sagrishin.smartreader.models.LibrariesModel
import com.sagrishin.smartreader.models.exceptions.LibraryModelException
import com.sagrishin.smartreader.models.repositories.LibrariesRepository
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.Library

class LibrariesModelImpl : LibrariesModel {

    private val librariesRepository: LibrariesRepository
    private val executor: Executor

    constructor(librariesRepository: LibrariesRepository, executor: Executor) {
        this.librariesRepository = librariesRepository
        this.executor = executor
    }

    @Throws(LibraryModelException::class)
    override fun getUserLibraries(email: String, start: Int, count: Int): List<Library> {
        return librariesRepository.getUserLibraries(email, start, count)
    }

    override fun getUserLibraries(email: String): List<Library> {
        return librariesRepository.getAllUserLibraries(email)
    }

    override fun isBookRelatesToUserLibrary(email: String, library: String, title: String): Boolean {
        return librariesRepository.isBookRelatesToUserLibrary(email, library, title)
    }

    override fun isBookFavoritesByUser(email: String, title: String): Boolean {
        return librariesRepository.isBookFavoritesByUser(email, title)
    }

    @Throws(LibraryModelException::class)
    override fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): Library {
        return librariesRepository.getBooksFromUserLibrary(email, library, start, count)
    }

    @Throws(LibraryModelException::class)
    override fun createNewUserLibrary(email: String, library: String): Library {
        return librariesRepository.createNewUserLibrary(email, library)
    }

    override fun addNewBookToUserLibrary(title: String, email: String, library: String): Boolean {
        return librariesRepository.addNewBookToUserLibrary(title, email, library)
    }

    override fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int): Boolean {
        return librariesRepository.updateBookReadingProgress(title, email, library, progress)
    }

    override fun deleteBookFromLibrary(title: String, email: String, library: String): Boolean {
        return librariesRepository.deleteBookFromLibrary(title, email, library)
    }

    override fun deleteUserLibrary(email: String, library: String): Boolean {
        return librariesRepository.deleteUserLibrary(email, library)
    }

}
