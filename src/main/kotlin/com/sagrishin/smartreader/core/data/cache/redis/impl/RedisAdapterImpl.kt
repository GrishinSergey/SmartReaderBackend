package com.sagrishin.smartreader.core.data.cache.redis.impl

import com.sagrishin.smartreader.core.data.cache.redis.KryoContext
import com.sagrishin.smartreader.core.data.cache.redis.RedisAdapter
import redis.clients.jedis.Jedis
import java.util.*

class RedisAdapterImpl : RedisAdapter {

    private val kryoContext: KryoContext
    private val jedis: Jedis

    constructor(kryoContext: KryoContext, jedis: Jedis) {
        this.kryoContext = kryoContext
        this.jedis = jedis

        this.jedis.flushAll()
    }

    @Throws(NoSuchElementException::class)
    override operator fun <T> get(keyName: String, prepared: T): T {
        val keyInBytes = keyName.toByteArray()
        jedis[keyInBytes]?.let {
            return kryoContext.deserialize(prepared, it)
        }?:let {
            throw NoSuchElementException("Nothing stored by key '$keyName'")
        }
    }

    override operator fun <T> set(keyName: String, expire: Int, value: T) {
        val keyInBytes = keyName.toByteArray()
        jedis[keyInBytes] = kryoContext.serialize(value)
        if (expire > 0) jedis.expire(keyInBytes, expire)
    }

}
