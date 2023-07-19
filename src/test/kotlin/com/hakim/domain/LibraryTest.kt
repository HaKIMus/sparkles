package com.hakim.domain

import com.hakim.domain.event.BookBorrowed
import com.hakim.domain.event.BookRegistered
import com.hakim.domain.event.LibraryInitialized
import org.junit.jupiter.api.Test
import java.util.*

class LibraryTest {
    @Test
    fun `create from initialized event`() {
        val aggregateId = AggregateId.random()
        val library = Library.libraryFromInitializedEvent(LibraryInitialized(aggregateId))

        assert(library.id == aggregateId)
    }

    @Test
    fun `create new clean instance`() {
        val id = AggregateId.random()
        val library = Library.newLibrary(id)

        assert(library.changes.first().aggregateId == id)
        assert(library.books.isEmpty())
        assert(library.readers.isEmpty())
    }

    @Test
    fun `apply book registered event test`() {
        val library = Library.newLibrary(AggregateId.random())

        val newBookId = UUID.randomUUID();
        library.apply(BookRegistered(library.id, newBookId))

        assert(library.books.isNotEmpty())
        assert(library.books.firstOrNull { it.id == newBookId } != null)
    }

    @Test
    fun `apply book borrowed event test`() {
        val library = Library.newLibrary(AggregateId.random())

        val newBookId = UUID.randomUUID();
        library.apply(BookRegistered(library.id, newBookId))
        library.apply(BookBorrowed(library.id, newBookId))
        assert(library.books.isEmpty())

        library.apply(BookRegistered(library.id, newBookId))
        assert(library.books.isNotEmpty())
        library.borrowBook(library.books.first())
        assert(library.books.isEmpty())
        assert(library.hasChanged())
    }

    @Test
    fun `borrow book from library test`() {
        val library = Library.newLibrary(AggregateId.random())

        val newBookId = UUID.randomUUID()
        library.apply(BookRegistered(library.id, newBookId))

        library.borrowBook(library.books.first())
        assert(library.books.isEmpty())
    }
}