package com.hakim.domain.event

import com.hakim.domain.AggregateId
import com.hakim.domain.Reader
import java.util.UUID

class ReaderRegistered(libraryId: AggregateId, val readerId: UUID) : DomainEvent(libraryId), TransformableDomainEvent<Reader> {
    override fun transform(): Reader {
        return Reader(readerId)
    }
}