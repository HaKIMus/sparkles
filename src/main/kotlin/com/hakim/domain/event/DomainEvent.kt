package com.hakim.domain.event

import com.hakim.domain.AggregateId
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone
import java.util.UUID

abstract class DomainEvent(val aggregateId: AggregateId) {
    val createdAt = LocalDateTime.now(ZoneId.of("UTC"))
}