package com.zm.borrowmoneyandriodapp.components;

import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * GuavaCache
 */
@Slf4j
@Component
public class CacheMap {

    @Autowired
    Cache cache;

    @Resource(name = "limitCache")
    Cache limitCache;

    public Object get(String key) {
        return StringUtils.isNotEmpty(key) ? cache.getIfPresent(key) : null;
    }

    @Async
    public void set(String key, Object value) {
        if (StringUtils.isNotEmpty(key) && value != null) {
            cache.put(key, value);
        }
    }

    @Async
    public void remove(String key) {
        if (StringUtils.isNotEmpty(key)) {
            cache.invalidate(key);
        }
    }

    @Async
    public void remove(List<String> keys) {
        if (keys != null && keys.size() > 0) {
            cache.invalidateAll(keys);
        }
    }

    public void resetCache() {
        cache.invalidateAll();
    }

    public void limitSet(String key, Object value) {
        if (StringUtils.isNotEmpty(key) && value != null) {
            limitCache.put(key, value);
        }
    }

    public Object limitGet(String key) {
        return StringUtils.isNotEmpty(key) ? limitCache.getIfPresent(key) : null;
    }

    public void limitRemove(String key) {
        if (StringUtils.isNotEmpty(key)) {
            limitCache.invalidate(key);
        }
    }
}
