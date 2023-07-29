package com.hakim.infrastructure.cache

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import com.hakim.domain.event.DomainEvent
import io.github.reactivecircus.cache4k.Cache
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

typealias DomainEventsCache = Cache<AggregateId, Flow<DomainEvent>>
typealias LibrariesCache = Cache<AggregateId, Library>

@Factory
class CacheFactory {
    @Bean
    @Singleton
    fun librariesCache(): LibrariesCache = Cache.Builder()
        .maximumCacheSize(100)
        .build()

    @Bean
    @Singleton
    fun domainEventsCache(): DomainEventsCache = Cache.Builder()
        .maximumCacheSize(100)
        .build()
}