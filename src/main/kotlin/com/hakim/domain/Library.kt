package com.hakim.domain

import com.hakim.domain.event.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Library private constructor(val id: AggregateId) : Aggregate(id) {
    @Transient private val _readers: MutableList<Reader> = mutableListOf()
    val readers = _readers.toList()

    @Transient private val _books: MutableList<Book> = mutableListOf()
    val books = _books.toList()

    @Transient private val _changes: MutableList<DomainEvent> = mutableListOf()
    val changes = _changes.toList()

    companion object {
        fun newLibrary(aggregateId: AggregateId): Library {
            val library = Library(aggregateId)
            library._changes.add(LibraryInitialized(aggregateId))

            return library
        }

        fun libraryFromLibraryInitialized(event: LibraryInitialized): Library {
            return Library(event.aggregateId)
        }
    }

    fun registerBook(book: Book): Library {
        _books.add(book)

        val event = BookRegistered(this.id, book.id)
        _changes.add(event)

        return this
    }

    fun borrowBook(borrowedBook: Book): Library {
        if (_books.firstOrNull { it == borrowedBook } == null) {
            TODO("throw a domain exception")
        }

        _books.remove(borrowedBook)

        val event = BookBorrowed(this.id, borrowedBook.id)
        _changes.add(event)

        return this
    }

    fun hasChanged(): Boolean {
        return _changes.isNotEmpty()
    }

    fun apply(event: BookRegistered) {
        _books.add(event.transform())
    }

    fun apply(event: BookBorrowed) {
        _books.remove(_books.first { it.id == event.bookId } )
    }

    fun apply(event: ReaderRegistered) {
        _readers.add(event.transform())
    }
}