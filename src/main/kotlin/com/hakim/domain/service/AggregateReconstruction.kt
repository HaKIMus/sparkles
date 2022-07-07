package com.hakim.domain.service

import com.hakim.domain.Aggregate
import com.hakim.domain.event.DomainEvent

interface AggregateReconstruction<T : Aggregate> {
    fun reconstruct(events: List<DomainEvent>): T
}