package com.hakim.domain.event

import com.hakim.domain.AggregateId
import java.util.UUID

class LibraryInitialized(libraryId: AggregateId) : DomainEvent(libraryId) {
}