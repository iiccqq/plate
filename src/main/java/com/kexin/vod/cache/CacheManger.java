package com.kexin.vod.cache;

import java.util.HashMap;
import java.util.Map;

public interface CacheManger {

	static Map<String, Object> cacheMap = new HashMap<String, Object>();

	static void set(String key, Object value) {
		cacheMap.put(key, value);
	}

	static Object get(String key) {
		return cacheMap.get(key);
	}

	static void remove(String key) {
		cacheMap.remove(key);
	}

}
