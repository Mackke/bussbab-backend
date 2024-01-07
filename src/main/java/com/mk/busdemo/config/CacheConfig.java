package com.mk.busdemo.config;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableCaching
@EnableScheduling
@RequiredArgsConstructor
public class CacheConfig {
    private static final Logger LOG = LoggerFactory.getLogger(CacheConfig.class);

    private final CacheManager cacheManager;

    //According to the Docs of the trafiklab-API the data is updated once every night
    //Cache TTL is set to 15 min for now
    @Scheduled(fixedRate = 1000 * 60 * 15, initialDelay = 15 * 1000 * 60)
    public void clearBusDataCache() {
        LOG.info("[Cache] Clearing BusDataCache");
        Optional.ofNullable(cacheManager.getCache("BusDataCache"))
                .ifPresent(Cache::clear);
    }
}
