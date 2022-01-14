package com.example.track_library.cache;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;

@Configuration
@EnableCaching
public class CacheConfiguration {
    @Bean
    public JCacheManagerCustomizer genresCacheConfigurationCustomizer() {
        return cm -> cm.createCache("genres", cacheConfiguration());
    }

    private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
        return new MutableConfiguration<>().setStatisticsEnabled(true);
    }
}
