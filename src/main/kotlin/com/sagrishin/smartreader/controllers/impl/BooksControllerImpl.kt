package com.sagrishin.smartreader.controllers.impl

import com.sagrishin.smartreader.api.responses.BooksResponse
import com.sagrishin.smartreader.api.responses.VoiceOversResponse
import com.sagrishin.smartreader.controllers.BooksController
import com.sagrishin.smartreader.controllers.Controller
import com.sagrishin.smartreader.models.BooksModel
import com.sagrishin.smartreader.models.repositories.models.Book

class BooksControllerImpl : BooksController {

    private val booksModel: BooksModel

    constructor(booksModel: BooksModel) {
        this.booksModel = booksModel
    }

    override fun getBooksByGenre(genre: String, start: Int, count: Int): BooksResponse<List<Book>> {
        val booksByGenre = booksModel.getBooksByGenre(genre, start, count)
        return BooksResponse(booksByGenre, Controller.SUCCESS_STATUS_CODE)
    }

    override fun getBookByTitleAndAuthor(title: String, author: String): BooksResponse<Book> {
        val bookByTitleAndAuthor = booksModel.getBookByTitleAndAuthor(title, author)
        return BooksResponse(bookByTitleAndAuthor, Controller.SUCCESS_STATUS_CODE)
    }

    override fun findBooksByEntry(entry: String, genre: String): BooksResponse<List<Book>> {
        val booksByEntry = booksModel.findBooksByEntry(entry, genre)
        return BooksResponse(booksByEntry, Controller.SUCCESS_STATUS_CODE)
    }

    override fun getBookVoiceOver(title: String, author: String): VoiceOversResponse {
        val voiceOvers = booksModel.getBookVoiceOvers(title, author)
        return VoiceOversResponse(voiceOvers, Controller.SUCCESS_STATUS_CODE)
    }

}
