package com.hakim.domain.event

import com.hakim.domain.AggregateId
import com.hakim.infrastructure.serialization.UuidSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.Instant
import java.util.*

@Serializable
data class BookBorrowed(
    val libraryId: AggregateId,
    @Serializable(with = UuidSerializer::class) val bookId: UUID,
    @Transient override val occurredOn: Date = Date.from(Instant.now())
) : DomainEvent(libraryId, occurredOn) {
}