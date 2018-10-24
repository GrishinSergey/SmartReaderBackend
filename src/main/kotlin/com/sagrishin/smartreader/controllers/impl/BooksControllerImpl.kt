package com.sagrishin.smartreader.controllers.impl

import com.google.gson.Gson
import com.sagrishin.smartreader.api.responses.BooksResponse
import com.sagrishin.smartreader.api.responses.VoiceOversResponse
import com.sagrishin.smartreader.controllers.BooksController
import com.sagrishin.smartreader.controllers.base.BaseController
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.BooksModel

class BooksControllerImpl : BaseController, BooksController {

    private val booksModel: BooksModel

    constructor(booksModel: BooksModel, gson: Gson) : super(gson) {
        this.booksModel = booksModel
    }

    override fun getBooksByGenre(genre: String, start: Int, count: Int): BooksResponse<List<Book>> {
        val booksByGenre = booksModel.getBooksByGenre(genre, start, count)
        return BooksResponse(booksByGenre, SUCCESS_STATUS_CODE)
    }

    override fun getBookByTitleAndAuthor(title: String, author: String): BooksResponse<Book> {
        val bookByTitleAndAuthor = booksModel.getBookByTitleAndAuthor(title, author)
        return BooksResponse(bookByTitleAndAuthor, SUCCESS_STATUS_CODE)
    }

    override fun findBooksByEntry(entry: String, genre: String): BooksResponse<List<Book>> {
        val booksByEntry = booksModel.findBooksByEntry(entry, genre)
        return BooksResponse(booksByEntry, SUCCESS_STATUS_CODE)
    }

    override fun getBookVoiceOver(title: String, author: String): VoiceOversResponse {
        val voiceOvers = booksModel.getBookVoiceOvers(title, author)
        return VoiceOversResponse(voiceOvers, SUCCESS_STATUS_CODE)
    }

}
