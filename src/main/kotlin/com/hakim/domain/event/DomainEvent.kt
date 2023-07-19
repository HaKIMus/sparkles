package com.hakim.domain.event

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.hakim.domain.AggregateId
import com.hakim.infrastructure.serialization.DateSerializer
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.*

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = LibraryInitialized::class, name = "library-initialized"),
    JsonSubTypes.Type(value = BookBorrowed::class, name = "book-borrowed"),
    JsonSubTypes.Type(value = BookRegistered::class, name = "book-registered"),
    JsonSubTypes.Type(value = ReaderRegistered::class, name = "reader-registered"),
)
@Serializable
abstract class DomainEvent(
    val aggregateId: AggregateId,
    @Serializable(with = DateSerializer::class) open val occurredOn: Date = Date.from(Instant.now())
)