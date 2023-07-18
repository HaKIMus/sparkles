package com.hakim.domain.service

import com.hakim.domain.Library
import com.hakim.domain.event.*
import jakarta.inject.Singleton
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

@Singleton
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

    override suspend fun reconstructAsync(events: Flow<DomainEvent>): Deferred<Library> = coroutineScope {
        var library: Library? = null

        val x = events.toList()
        x.forEach {
            println(it.occurredOn)
        }

        events.collect { event ->
            when (event) {
                is LibraryInitialized -> {
                    if (library == null) {
                        library = createLibraryFromFirstEvent(event)
                    } else {
                        throw Exception("Multiple init events") // TODO: domain exception
                    }
                }
                else -> {
                    if (library != null) {
                        mapEventToLibrary(library!!, event)
                    } else {
                        throw Exception("No init event") // TODO: domain exception
                    }
                }
            }
        }


        return@coroutineScope async {
            library ?: throw IllegalStateException("No library was created")
        }    }

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

        return Library.libraryFromLibraryInitialized(first)
    }
}