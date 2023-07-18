package com.hakim.domain.event

import com.hakim.domain.AggregateId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.Instant
import java.util.*

@Serializable
data class LibraryInitialized(
    val libraryId: AggregateId,
    @Transient override val occurredOn: Date = Date.from(Instant.now())
) : DomainEvent(libraryId, occurredOn) {

}