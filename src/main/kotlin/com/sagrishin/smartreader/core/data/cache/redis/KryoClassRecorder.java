package com.sagrishin.smartreader.core.data.cache.redis;

import com.esotericsoftware.kryo.Kryo;

public interface KryoClassRecorder {
	
	void register(Kryo kryo);

}
