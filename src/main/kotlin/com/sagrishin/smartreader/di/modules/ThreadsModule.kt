package com.sagrishin.smartreader.di.modules

import com.sagrishin.smartreader.core.threads.Executor
import com.sagrishin.smartreader.core.threads.impl.ExecutorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
@Singleton
class ThreadsModule {

    @Provides
    @Singleton
    @Named(value = "ServerThreadsPool")
    fun getServerRequestsExecutor(): Executor {
        return ExecutorImpl(ExecutorImpl.DEFAULT_THREADS_COUNT)
    }

    @Provides
    @Singleton
    @Named(value = "OneThreadExecutor")
    fun getOneThreadExecutor(): Executor {
        return ExecutorImpl(1)
    }

}
