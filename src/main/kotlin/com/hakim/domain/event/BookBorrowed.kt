package com.hakim.domain.event

import com.hakim.domain.AggregateId
import java.util.UUID

class BookBorrowed(libraryId: AggregateId, val bookId: UUID) : DomainEvent(libraryId) {
}