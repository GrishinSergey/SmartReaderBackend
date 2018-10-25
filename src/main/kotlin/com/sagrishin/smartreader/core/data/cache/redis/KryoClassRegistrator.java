package com.sagrishin.smartreader.core.data.cache.redis;

import com.esotericsoftware.kryo.Kryo;

public interface KryoClassRegistrator {
	
	void register(Kryo kryo);

}
