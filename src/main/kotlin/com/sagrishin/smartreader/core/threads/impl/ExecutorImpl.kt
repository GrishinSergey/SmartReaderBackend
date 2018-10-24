package com.sagrishin.smartreader.core.threads.impl

import com.sagrishin.smartreader.core.threads.Executor
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ExecutorImpl(threadsCount: Int = DEFAULT_THREADS_COUNT) : Executor {

    companion object {
        @JvmField
        val DEFAULT_THREADS_COUNT = Runtime.getRuntime().availableProcessors()
    }

    private val threadPool = Executors.newFixedThreadPool(threadsCount)

    override fun <T> execute(task: Callable<T>): Future<T> {
        return threadPool.submit(task)
    }

}
