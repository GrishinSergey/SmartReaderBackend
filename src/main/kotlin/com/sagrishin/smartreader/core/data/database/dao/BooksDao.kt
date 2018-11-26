package com.sagrishin.smartreader.core.data.database.dao

import com.sagrishin.smartreader.core.data.models.DatabaseBook
import com.sagrishin.smartreader.core.data.models.DatabaseVoiceOver

interface BooksDao {

    fun getBooksByGenre(genreName: String, start: Int, count: Int): List<DatabaseBook>

    fun getBookByTitleAndAuthor(bookTitle: String, author: String): DatabaseBook

    fun findBooksByEntry(entry: String, genreName: String): List<DatabaseBook>

    fun getBookVoiceOvers(bookTitle: String, author: String): List<DatabaseVoiceOver>

}