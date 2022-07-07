package com.hakim.domain.event

import java.util.UUID

class LibraryInitialized(id: UUID) : DomainEvent(id) {
}