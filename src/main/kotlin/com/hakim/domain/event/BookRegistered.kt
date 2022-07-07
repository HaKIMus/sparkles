package com.hakim.domain.event

import com.hakim.domain.AggregateId
import com.hakim.domain.Book
import java.util.UUID

class BookRegistered(libraryId: AggregateId, val bookId: UUID) : DomainEvent(libraryId), TransformableDomainEvent<Book> {
    override fun transform(): Book {
        return Book(bookId)
    }
}