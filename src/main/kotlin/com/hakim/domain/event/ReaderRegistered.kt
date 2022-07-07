package com.hakim.domain.event

import com.hakim.domain.Reader
import java.util.UUID

class ReaderRegistered(id: UUID) : DomainEvent(id), TransformableDomainEvent<Reader> {
    override fun transform(): Reader {
        return Reader(id)
    }
}