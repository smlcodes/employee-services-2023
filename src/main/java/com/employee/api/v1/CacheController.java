package com.employee.api.v1;

import com.employee.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author satyakaveti
 */
@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "cache")
@Slf4j
public class CacheController {
    @Autowired
    CacheManager cacheManager;

    @GetMapping("/all")
    public Map<String, Object> getAllCachedData() {
        Map<String, Object> cachedDataMap = new HashMap<>();

        // Get the names of all caches managed by the CacheManager
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            log.info("Cache Name: {} , Cache: {} ", cacheName, cacheManager);

            cachedDataMap.put(cacheName, cache);
        }
        return cachedDataMap;
    }
}