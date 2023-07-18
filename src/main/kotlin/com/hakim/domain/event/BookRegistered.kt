package com.hakim.domain.event

import com.hakim.domain.AggregateId
import com.hakim.domain.Book
import com.hakim.infrastructure.serialization.UuidSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.Instant
import java.util.*

@Serializable
data class BookRegistered(
    val libraryId: AggregateId,
    @Serializable(with = UuidSerializer::class) val bookId: UUID,
    @Transient override val occurredOn: Date = Date.from(Instant.now())
) : DomainEvent(libraryId, occurredOn), TransformableDomainEvent<Book> {
    override fun transform(): Book {
        return Book(bookId)
    }
}