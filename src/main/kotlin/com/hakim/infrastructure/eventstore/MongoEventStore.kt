package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import com.hakim.infrastructure.storage.MongoClient
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import org.litote.kmongo.eq

@Singleton
@EventStoreQualifier
class MongoEventStore(
    @Inject private val client: MongoClient,
    @Named("ioDispatcher") private val dispatcher: CoroutineDispatcher
) : EventStore {
    private val collection = client.database.getCollection<DomainEvent>()

    override suspend fun persist(event: DomainEvent) {
        collection.insertOne(event)
    }

    override suspend fun persist(events: List<DomainEvent>) {
        collection.insertMany(events)
    }

    override suspend fun read(aggregateId: AggregateId): Flow<DomainEvent> {
        return collection
            .find(DomainEvent::aggregateId eq aggregateId)
            .ascendingSort(DomainEvent::occurredOn)
            .toFlow()
    }

    override suspend fun readAll(): Flow<Flow<DomainEvent>> {
        val events = collection
            .find()
            .ascendingSort(DomainEvent::occurredOn)
            .toList()

        val groupedEvents = events.groupBy { it.aggregateId }

        return flow {
            groupedEvents.forEach { (_, events) ->
                emit(events.asFlow())
            }
        }
    }
}