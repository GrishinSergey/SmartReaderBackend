package com.sagrishin.smartreader.core.data.cache.redis.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.sagrishin.smartreader.core.data.cache.redis.KryoClassRecorder;
import com.sagrishin.smartreader.core.data.cache.redis.KryoContext;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

public class KryoContextImpl implements KryoContext {

    private static final int DEFAULT_BUFFER = 1024 * 100;

    private KryoPool pool;

    public static KryoContext newKryoContextFactory(KryoClassRecorder registrator) {
        return new KryoContextImpl(registrator);
    }

    private KryoContextImpl(KryoClassRecorder registrator) {
        KryoFactory factory = new KryoFactoryImpl(registrator);
        pool = new KryoPool.Builder(factory).softReferences().build();
    }

    @NotNull
    @Override
    public byte[] serialize(@NotNull Object obj) {
        return serialize(obj, DEFAULT_BUFFER);
    }

    @NotNull
    @Override
    public byte[] serialize(@NotNull Object obj, int bufferSize) {
        Output output = new Output(new ByteArrayOutputStream(), bufferSize);
        Kryo kryo = pool.borrow();
        kryo.writeObject(output, obj);
        byte[] serialized = output.toBytes();
        pool.release(kryo);
        return serialized;
    }

    @Override
    public <T> T deserialize(T type, @NotNull byte[] serialized) {
        T obj;
        Kryo kryo = pool.borrow();
        Input input = new Input(serialized);
        obj = (T) kryo.readObject(input, type.getClass());
        pool.release(kryo);
        return obj;
    }


    private static class KryoFactoryImpl implements KryoFactory {

        private KryoClassRecorder recorder;

        KryoFactoryImpl(KryoClassRecorder recorder) {
            this.recorder = recorder;
        }

        @Override
        public Kryo create() {
            Kryo kryo = new Kryo();
            recorder.register(kryo);
            return kryo;
        }

    }

}
