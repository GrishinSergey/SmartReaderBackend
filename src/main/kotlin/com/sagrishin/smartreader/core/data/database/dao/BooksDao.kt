package com.sagrishin.smartreader.core.data.database.dao

import com.sagrishin.smartreader.core.data.database.entities.DatabaseBook
import com.sagrishin.smartreader.core.data.database.entities.DatabaseVoiceOver
import com.sagrishin.smartreader.core.data.database.exceptions.DuplicatedDataInDatabaseException
import com.sagrishin.smartreader.core.data.database.exceptions.NothingFoundInDatabaseException

interface BooksDao {

    @Throws(NothingFoundInDatabaseException::class)
    fun getBooksByGenre(genreName: String, start: Int, count: Int): List<DatabaseBook>

    @Throws(NothingFoundInDatabaseException::class, DuplicatedDataInDatabaseException::class)
    fun getBookByTitleAndAuthor(bookTitle: String, author: String): DatabaseBook

    @Throws(NothingFoundInDatabaseException::class)
    fun findBooksByEntry(entry: String, genreName: String): List<DatabaseBook>

    @Throws(NothingFoundInDatabaseException::class, DuplicatedDataInDatabaseException::class)
    fun getBookVoiceOvers(bookTitle: String, author: String): List<DatabaseVoiceOver>

}