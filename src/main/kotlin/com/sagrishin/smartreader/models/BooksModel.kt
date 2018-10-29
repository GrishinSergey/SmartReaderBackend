package com.sagrishin.smartreader.models

import com.sagrishin.smartreader.models.exceptions.BookModelException
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.VoiceOvers

interface BooksModel {

    @Throws(BookModelException::class)
    fun getBooksByGenre(genreName: String, start: Int, count: Int): List<Book>

    @Throws(BookModelException::class)
    fun getBookByTitleAndAuthor(bookTitle: String, author: String): Book

    @Throws(BookModelException::class)
    fun findBooksByEntry(entry: String, genreName: String): List<Book>

    @Throws(BookModelException::class)
    fun getBookVoiceOvers(bookTitle: String, author: String): VoiceOvers

}