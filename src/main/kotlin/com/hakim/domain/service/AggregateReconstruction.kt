package com.hakim.domain.service

import com.hakim.domain.Aggregate
import com.hakim.domain.event.DomainEvent
import kotlinx.coroutines.flow.Flow

interface AggregateReconstruction<T : Aggregate> {
    fun reconstruct(events: List<DomainEvent>): T

    suspend fun reconstruct(events: Flow<DomainEvent>): T
}