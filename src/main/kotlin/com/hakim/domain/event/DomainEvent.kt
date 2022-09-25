package com.hakim.domain.event

import com.hakim.domain.AggregateId
import java.time.Instant
import java.util.*

abstract class DomainEvent(val aggregateId: AggregateId) {
    val occurredOn: Date = Date.from(Instant.now())
}