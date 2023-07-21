package com.hakim.infrastructure.eventstore.proxy

import com.hakim.domain.AggregateId

interface ReadEventStoreProxy<T> {
    suspend fun read(aggregateId: AggregateId): T

    suspend fun readAll(): List<T>
}