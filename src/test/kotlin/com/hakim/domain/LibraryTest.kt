package com.hakim.domain

import com.hakim.domain.event.BookBorrowed
import com.hakim.domain.event.BookRegistered
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class LibraryTest {
    @Test
    fun `create new clean instance`() {
        val library = Library(
            UUID.randomUUID(),
            mutableListOf(),
            mutableListOf(),
        )

        assert(library.books.isEmpty())
        assert(library.readers.isEmpty())
    }

    @Test
    fun `apply book registered event test`() {
        val library = Library(
            UUID.randomUUID(),
            mutableListOf(),
            mutableListOf(),
        )

        val newBookId = UUID.randomUUID();
        library.apply(BookRegistered(newBookId))

        assert(library.books.isNotEmpty())
        assert(library.books.firstOrNull { it.id == newBookId } != null)
    }

    @Test
    fun `apply book borrowed event test`() {
        val library = Library(
            UUID.randomUUID(),
            mutableListOf(),
            mutableListOf(),
        )

        val newBookId = UUID.randomUUID();
        library.apply(BookRegistered(newBookId))
        library.apply(BookBorrowed(newBookId))
        assert(library.books.isEmpty())

        library.apply(BookRegistered(newBookId))
        assert(library.books.isNotEmpty())
        library.borrowBook(library.books.first())
        assert(library.books.isEmpty())
    }

    @Test
    fun `borrow book from library test`() {
        val library = Library(
            UUID.randomUUID(),
            mutableListOf(),
            mutableListOf(),
        )

        val newBookId = UUID.randomUUID();
        library.apply(BookRegistered(newBookId))

        library.borrowBook(library.books.first())
        assert(library.books.isEmpty())
    }
}