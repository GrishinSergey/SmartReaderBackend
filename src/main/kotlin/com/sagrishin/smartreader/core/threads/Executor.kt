package com.sagrishin.smartreader.core.threads

import java.util.concurrent.Callable
import java.util.concurrent.Future

interface Executor {

    fun <T> execute(task: Callable<T>): Future<T>

}