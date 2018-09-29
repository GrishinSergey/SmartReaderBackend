package com.sagrishin.smartreader.models

import com.sagrishin.smartreader.core.data.repositories.GenresRepository
import com.sagrishin.smartreader.core.data.repositories.models.Genre
import com.sagrishin.smartreader.core.threads.Executor
import java.util.concurrent.Callable

class GenresModel() {

    private lateinit var repository: GenresRepository
    private lateinit var threadsExecutor: Executor

    constructor(repository: GenresRepository, threadsExecutor: Executor): this() {
        this.repository = repository
        this.threadsExecutor = threadsExecutor
    }

    fun getAllGenres(): List<Genre> {
        return threadsExecutor.execute(Callable { repository.getAllGenres() }).get()
    }

}
