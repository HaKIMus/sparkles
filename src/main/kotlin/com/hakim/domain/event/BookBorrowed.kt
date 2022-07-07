package com.hakim.domain.event

import java.util.UUID

class BookBorrowed(val bookId: UUID) : DomainEvent(bookId) {
}