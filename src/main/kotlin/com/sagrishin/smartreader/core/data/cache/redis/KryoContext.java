package com.sagrishin.smartreader.core.data.cache.redis;

/**
 * Created by mykidong on 2018-06-07.
 */
public interface KryoContext {

    byte[] serialize(Object obj);

    byte[] serialize(Object obj, int bufferSize);

    <T> T deserialize(T type, byte[] serialized) throws ClassNotFoundException;

}
