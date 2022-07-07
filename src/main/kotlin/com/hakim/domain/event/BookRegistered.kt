package com.hakim.domain.event

import com.hakim.domain.Book
import java.util.UUID

class BookRegistered(id: UUID) : DomainEvent(id), TransformableDomainEvent<Book> {
    override fun transform(): Book {
        return Book(id)
    }
}