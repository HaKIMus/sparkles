package com.hakim.infrastructure.cache

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import io.github.reactivecircus.cache4k.Cache
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
class CacheFactory {
    @Bean
    @Singleton
    fun librariesCache(): Cache<AggregateId, Library> = Cache.Builder()
        .maximumCacheSize(100)
        .build()
}