package com.hakim.infrastructure.eventstore.service

import com.hakim.domain.Aggregate
import com.hakim.domain.AggregateId

interface ReconstructAggregate<T : Aggregate> {
    suspend fun reconstruct(aggregateId: AggregateId): T

    suspend fun reconstructAll(): List<T>
}