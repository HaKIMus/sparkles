package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import com.hakim.infrastructure.storage.MongoClient
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.litote.kmongo.eq

@Singleton
class MongoEventStore(@Inject private val client: MongoClient) : EventStore {
    private val collection = client.database.getCollection<DomainEvent>()

    override suspend fun persist(event: DomainEvent) = withContext<Unit>(Dispatchers.IO) {
        collection.insertOne(event)
    }

    override suspend fun persist(events: List<DomainEvent>) = withContext<Unit>(Dispatchers.IO) {
        collection.insertMany(events)
    }

    override suspend fun read(aggregateId: AggregateId): Flow<DomainEvent> = withContext(Dispatchers.IO) {
        return@withContext collection
            .find(DomainEvent::aggregateId eq aggregateId)
            .ascendingSort(DomainEvent::occurredOn)
            .toFlow()
    }
}