package com.sagrishin.smartreader.models.impl

import com.sagrishin.smartreader.models.repositories.GenresRepository
import com.sagrishin.smartreader.models.repositories.models.Genre
import com.sagrishin.smartreader.core.threads.Executor
import com.sagrishin.smartreader.models.GenresModel
import java.util.concurrent.Callable

class GenresModelImpl : GenresModel {

    private val repository: GenresRepository
    private val threadsExecutor: Executor

    constructor(repository: GenresRepository, threadsExecutor: Executor) {
        this.repository = repository
        this.threadsExecutor = threadsExecutor
    }

    override fun getAllGenres(): List<Genre> {
        return threadsExecutor.execute(Callable { repository.getAllGenres() }).get()
    }

}
