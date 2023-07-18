package com.hakim.domain

import com.hakim.domain.event.*
import kotlinx.serialization.Serializable

@Serializable
data class Library(val id: AggregateId) : Aggregate(id) {
    val readers: MutableList<Reader> = mutableListOf()
    val books: MutableList<Book> = mutableListOf()

    val changes: MutableList<DomainEvent> = mutableListOf()

    init {
        changes.add(LibraryInitialized(id))
    }

    fun registerBook(book: Book): Library {
        books.add(book)

        val event = BookRegistered(this.id, book.id)
        changes.add(event)

        return this
    }

    fun borrowBook(borrowedBook: Book): Library {
        if (books.firstOrNull { it == borrowedBook } == null) {
            TODO("throw a domain exception")
        }

        books.remove(borrowedBook)

        val event = BookBorrowed(this.id, borrowedBook.id)
        changes.add(event)

        return this
    }

    fun hasChanged(): Boolean {
        return changes.isNotEmpty()
    }

    fun apply(event: BookRegistered) {
        books.add(event.transform())
        changes.add(event)
    }

    fun apply(event: BookBorrowed) {
        books.remove(books.first { it.id == event.bookId } )
        changes.add(event)
    }

    fun apply(event: ReaderRegistered) {
        readers.add(event.transform())
        changes.add(event)
    }
}