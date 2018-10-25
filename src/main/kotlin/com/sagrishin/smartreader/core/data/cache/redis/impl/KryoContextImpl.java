package com.sagrishin.smartreader.core.data.cache.redis.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.sagrishin.smartreader.core.data.cache.redis.KryoClassRegistrator;
import com.sagrishin.smartreader.core.data.cache.redis.KryoContext;

import java.io.ByteArrayOutputStream;

public class KryoContextImpl implements KryoContext {

    private static final int DEFAULT_BUFFER = 1024 * 100;

    private KryoPool pool;

    public static KryoContext newKryoContextFactory(KryoClassRegistrator registrator) {
        return new KryoContextImpl(registrator);
    }

    private KryoContextImpl(KryoClassRegistrator registrator) {
        KryoFactory factory = new KryoFactoryImpl(registrator);
        pool = new KryoPool.Builder(factory).softReferences().build();
    }

    @Override
    public byte[] serialize(Object obj) {
        return serialize(obj, DEFAULT_BUFFER);
    }

    @Override
    public byte[] serialize(Object obj, int bufferSize) {
        Output output = new Output(new ByteArrayOutputStream(), bufferSize);
        Kryo kryo = pool.borrow();
        kryo.writeObject(output, obj);
        byte[] serialized = output.toBytes();
        pool.release(kryo);
        return serialized;
    }

    @Override
    public <T> T deserialize(T type, byte[] serialized) throws ClassNotFoundException {
        Object obj;
        Kryo kryo = pool.borrow();
        Input input = new Input(serialized);
        obj = kryo.readObject(input, Class.forName(type.getClass().getName()));
        pool.release(kryo);
        return (T) obj;
    }


    private static class KryoFactoryImpl implements KryoFactory {

        private KryoClassRegistrator registrator;

        KryoFactoryImpl(KryoClassRegistrator registrator) {
            this.registrator = registrator;
        }

        @Override
        public Kryo create() {
            Kryo kryo = new Kryo();
            registrator.register(kryo);
            return kryo;
        }

    }

}
