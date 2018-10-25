package com.sagrishin.smartreader.core.data.cache.redis

import java.util.NoSuchElementException

interface RedisAdapter {

    @Throws(NoSuchElementException::class)
    operator fun <T> get(keyName: String, prepared: T): T

    operator fun <T> set(keyName: String, expire: Int = 0, value: T)

}