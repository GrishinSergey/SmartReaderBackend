package com.sagrishin.smartreader.controllers

import com.sagrishin.smartreader.api.responses.BooksResponse
import com.sagrishin.smartreader.api.responses.VoiceOversResponse
import com.sagrishin.smartreader.models.repositories.models.Book

interface BooksController : Controller {

    fun getBooksByGenre(genre: String, start: Int, count: Int): BooksResponse<List<Book>>

    fun getBookByTitleAndAuthor(title: String, author: String): BooksResponse<Book>

    fun findBooksByEntry(entry: String, genre: String): BooksResponse<List<Book>>

    fun getBookVoiceOver(title: String, author: String): VoiceOversResponse

}
