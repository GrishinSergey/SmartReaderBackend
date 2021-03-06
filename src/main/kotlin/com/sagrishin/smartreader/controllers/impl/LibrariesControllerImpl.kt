package com.sagrishin.smartreader.controllers.impl

import com.sagrishin.smartreader.api.responses.*
import com.sagrishin.smartreader.controllers.Controller
import com.sagrishin.smartreader.controllers.LibrariesController
import com.sagrishin.smartreader.models.LibrariesModel
import com.sagrishin.smartreader.models.exceptions.LibraryModelException

class LibrariesControllerImpl : LibrariesController {

    private val librariesModel: LibrariesModel

    constructor(librariesModel: LibrariesModel) {
        this.librariesModel = librariesModel
    }

    override fun getUserLibraries(email: String, start: Int, count: Int): LibraryResponse<List<ResponseLibrary>> {
        if ((start >= 0) && (count > 0)) {
            val userLibraries = librariesModel.getUserLibraries(email, start, count)
            val libraries = userLibraries.map {
                ResponseLibrary(it.libraryName, it.countBooks, it.pathToCover, it.books)
            }
            return LibraryResponse(libraries, Controller.SUCCESS_STATUS_CODE)
        }
        return LibraryResponse(emptyList(), Controller.BAD_REQUEST_CODE)
    }

    override fun getAllUserLibraries(email: String): LibraryResponse<List<ResponseLibrary>> {
        val userLibraries = librariesModel.getUserLibraries(email)
        val libraries = userLibraries.map {
            ResponseLibrary(it.libraryName, it.countBooks, it.pathToCover, it.books)
        }
        return LibraryResponse(libraries, Controller.SUCCESS_STATUS_CODE)
    }

    override fun isBookRelatesToUserLibrary(email: String, library: String, title: String): Boolean {
        return librariesModel.isBookRelatesToUserLibrary(email, library, title)
    }

    override fun isBookFavoritesByUser(email: String, title: String): Boolean {
        return librariesModel.isBookFavoritesByUser(email, title)
    }

    override fun getBooksFromUserLibrary(email: String, library: String, start: Int, count: Int): ResponseLibrary {
        if ((start >= 0) && (count > 0)) {
            val found = librariesModel.getBooksFromUserLibrary(email, library, start, count)
            return ResponseLibrary(found.libraryName, found.countBooks, found.pathToCover, found.books)
        }
        return ResponseLibrary(library, -1, "", emptyList())
    }

    override fun createNewUserLibrary(email: String, library: String): LibraryActionResponse<Boolean> {
        return try {
            val (_, _) = librariesModel.createNewUserLibrary(email, library)
            LibraryActionResponse(LibraryAction.CREATE, true, Controller.SUCCESS_STATUS_CODE)
        } catch (e: Exception) {
            LibraryActionResponse(LibraryAction.CREATE, false, Controller.SERVER_ERROR_CODE)
        }
    }

    override fun addNewBookToUserLibrary(title: String, email: String, library: String
    ): LibraryActionResponse<Boolean> {
        return try {
            val addingResult = librariesModel.addNewBookToUserLibrary(title, email, library)
            if (addingResult) {
                LibraryActionResponse(LibraryAction.ADD_BOOK, addingResult, Controller.SUCCESS_STATUS_CODE)
            } else {
                LibraryActionResponse(LibraryAction.ADD_BOOK, addingResult, Controller.BAD_REQUEST_CODE)
            }
        } catch (e: Exception) {
            LibraryActionResponse(LibraryAction.ADD_BOOK, false, Controller.SERVER_ERROR_CODE)
        }
    }

    override fun updateBookReadingProgress(title: String, email: String, library: String, progress: Int
    ): LibraryActionResponse<Boolean> {
        return try {
            val progressUpdateResult = librariesModel.updateBookReadingProgress(title, email, library, progress)
            if (progressUpdateResult) {
                LibraryActionResponse(LibraryAction.UPDATE_BOOK, progressUpdateResult, Controller.SUCCESS_STATUS_CODE)
            } else {
                LibraryActionResponse(LibraryAction.UPDATE_BOOK, progressUpdateResult, Controller.BAD_REQUEST_CODE)
            }
        } catch (e: Exception) {
            LibraryActionResponse(LibraryAction.UPDATE_BOOK, false, Controller.SERVER_ERROR_CODE)
        }
    }

    override fun deleteBookFromLibrary(title: String, email: String, library: String
    ): LibraryActionResponse<Boolean> {
        return try {
            val deleteResult = librariesModel.deleteBookFromLibrary(title, email, library)
            LibraryActionResponse(LibraryAction.DELETE_BOOK, deleteResult, Controller.SUCCESS_STATUS_CODE)
        } catch (e: LibraryModelException) {
            LibraryActionResponse(LibraryAction.DELETE_BOOK, false, Controller.NOT_FOUND_CODE)
        } catch (e: Exception) {
            LibraryActionResponse(LibraryAction.DELETE_BOOK, false, Controller.SERVER_ERROR_CODE)
        }
    }

    override fun deleteUserLibrary(email: String, library: String): LibraryActionResponse<Boolean> {
        return try {
            val deleteResult = librariesModel.deleteUserLibrary(email, library)
            LibraryActionResponse(LibraryAction.DELETE_LIBRARY, deleteResult, Controller.SUCCESS_STATUS_CODE)
        } catch (e: LibraryModelException) {
            LibraryActionResponse(LibraryAction.DELETE_LIBRARY, false, Controller.NOT_FOUND_CODE)
        } catch (e: Exception) {
            LibraryActionResponse(LibraryAction.DELETE_LIBRARY, false, Controller.SERVER_ERROR_CODE)
        }
    }

}