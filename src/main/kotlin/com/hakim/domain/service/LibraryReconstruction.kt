package com.hakim.domain.service

import com.hakim.domain.Library
import com.hakim.domain.event.*
import com.hakim.domain.exception.AggregateReconstructionException
import com.hakim.domain.exception.LibraryNotInitializedException
import com.hakim.domain.exception.UnsupportedEvent
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Singleton
class LibraryReconstruction : AggregateReconstruction<Library> {
    override fun reconstruct(events: List<DomainEvent>): Library {
        val sortedEvents = events.sortedBy { it.occurredOn }.toMutableList()

        if (sortedEvents.first() !is LibraryInitialized) {
            throw AggregateReconstructionException("No init event")
        }

        val library = createLibraryFromFirstEvent(sortedEvents.first())
        sortedEvents.removeFirst()
        sortedEvents.forEach {
            mapEventToLibrary(library, it)
        }

        return library
    }

    /**
     * @param events It's assumed that the flow contains sorted domain events
     */
    override suspend fun reconstruct(events: Flow<DomainEvent>): Library {
        validateEvents(events)
        return reconstructLibraryFromEvents(events).getOrElse { throw it }
    }

    private suspend fun reconstructLibraryFromEvents(events: Flow<DomainEvent>): Result<Library> {
        var libraryResult: Result<Library> = Result.failure(LibraryNotInitializedException())

        events.collect { event ->
            libraryResult = processEvent(libraryResult, event)
        }

        return libraryResult
    }

    private fun processEvent(existingLibrary: Result<Library>, event: DomainEvent): Result<Library> {
        return if (event is LibraryInitialized) {
            Result.success(createLibraryFromFirstEvent(event))
        } else {
            existingLibrary.fold(
                onFailure = { Result.failure(LibraryNotInitializedException()) },
                onSuccess = { mapEventToLibrary(it, event); Result.success(it) }
            )
        }
    }

    private suspend fun validateEvents(events: Flow<DomainEvent>) {
        require(events.filter { it is LibraryInitialized }.count() == 1) {
            "There must be exactly one LibraryInitialized event"
        }

        require(events.first() is LibraryInitialized) {
            "LibraryInitialized event must be the first event"
        }
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
                throw UnsupportedEvent("Unsupported event: ${event::class}")
            }
        }
    }

    private fun createLibraryFromFirstEvent(first: DomainEvent): Library {
        if (first !is LibraryInitialized) throw Exception()

        return Library.libraryFromInitializedEvent(first)
    }
}