package com.hakim.domain.service

import com.hakim.domain.Library
import com.hakim.domain.event.*

class LibraryReconstruction : AggregateReconstruction<Library> {
    override fun reconstruct(events: List<DomainEvent>): Library {
        val sortedEvents = events.sortedBy { it.occurredOn }.toMutableList()

        if (sortedEvents.first() !is LibraryInitialized) {
            throw Exception("No init event") // TODO: domain exception
        }

        val library = createLibraryFromFirstEvent(sortedEvents.first())
        sortedEvents.removeFirst()
        sortedEvents.forEach {
            mapEventToLibrary(library, it)
        }

        return library
    }

    private fun mapEventToLibrary(library: Library, event: DomainEvent) {
        when(event) {
            is BookRegistered -> {
                library.apply(event)
            }
            is BookBorrowed -> {
                library.apply(event)
            }
            is ReaderRegistered -> {
                library.apply(event)
            }
            else -> {
                throw Exception("Unsupported event: ${event::class}")
            }
        }
    }

    private fun createLibraryFromFirstEvent(first: DomainEvent): Library {
        if (first !is LibraryInitialized) throw Exception()

        return Library(first.aggregateId)
    }
}