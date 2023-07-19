package com.hakim.domain.event

import com.hakim.domain.AggregateId
import com.hakim.domain.Reader
import com.hakim.infrastructure.serialization.UuidSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.Instant
import java.util.*

@Serializable
data class ReaderRegistered(
    val libraryId: AggregateId,
    @Serializable(with = UuidSerializer::class) val readerId: UUID,
    @Transient override val occurredOn: Date = Date.from(Instant.now())
) : DomainEvent(libraryId, occurredOn), TransformableDomainEvent<Reader> {
    override fun transform(): Reader {
        return Reader(readerId)
    }
}