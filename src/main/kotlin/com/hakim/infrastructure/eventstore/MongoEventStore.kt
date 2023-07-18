package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import com.hakim.infrastructure.storage.MongoClient
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

@Singleton
class MongoEventStore(@Inject private val client: MongoClient) : EventStore {
    private val collection = client.database.getCollection<DomainEvent>()

    override suspend fun persist(event: DomainEvent) {
        collection.insertOne(event)
    }

    override suspend fun persist(events: List<DomainEvent>) {
        collection.insertMany(events)
    }

    override suspend fun read(aggregateId: AggregateId): Flow<DomainEvent> {
        return collection.find(DomainEvent::aggregateId eq aggregateId).asFlow()
    }
}