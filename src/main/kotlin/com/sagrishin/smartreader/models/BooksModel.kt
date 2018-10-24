package com.sagrishin.smartreader.models

import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.VoiceOvers

interface BooksModel {

    fun getBooksByGenre(genreName: String, start: Int, count: Int): List<Book>

    fun getBookByTitleAndAuthor(bookTitle: String, author: String): Book

    fun findBooksByEntry(entry: String, genreName: String): List<Book>

    fun getBookVoiceOvers(bookTitle: String, author: String): VoiceOvers

}