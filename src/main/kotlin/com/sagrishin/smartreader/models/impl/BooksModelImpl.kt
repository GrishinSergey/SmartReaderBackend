package com.sagrishin.smartreader.models.impl

import com.sagrishin.smartreader.models.repositories.BooksRepository
import com.sagrishin.smartreader.models.repositories.models.Book
import com.sagrishin.smartreader.models.repositories.models.VoiceOvers
import com.sagrishin.smartreader.core.threads.Executor
import com.sagrishin.smartreader.models.BooksModel
import com.sagrishin.smartreader.models.exceptions.BookModelException

class BooksModelImpl : BooksModel {

    private val booksRepository: BooksRepository
    private val executor: Executor

    constructor(repository: BooksRepository, executor: Executor) {
        this.booksRepository = repository
        this.executor = executor
    }

    @Throws(BookModelException::class)
    override fun getBooksByGenre(genreName: String, start: Int, count: Int): List<Book> {
        return booksRepository.getBooksByGenre(genreName, start, count)
    }

    @Throws(BookModelException::class)
    override fun getBookByTitleAndAuthor(bookTitle: String, author: String): Book {
        return booksRepository.getBookByTitleAndAuthor(bookTitle, author)
    }

    @Throws(BookModelException::class)
    override fun findBooksByEntry(entry: String, genreName: String): List<Book> {
        return booksRepository.findBooksByEntry(entry, genreName)
    }

    @Throws(BookModelException::class)
    override fun getBookVoiceOvers(bookTitle: String, author: String): VoiceOvers {
        return booksRepository.getBookVoiceOvers(bookTitle, author)
    }

}
