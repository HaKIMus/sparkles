package com.hakim.domain

import com.hakim.domain.event.*

data class Library(val id: AggregateId) : Aggregate(id) {
    val readers: MutableList<Reader> = mutableListOf()
    val books: MutableList<Book> = mutableListOf()

    val changes: MutableList<DomainEvent> = mutableListOf()

    init {
        changes.add(LibraryInitialized(id))
    }

    fun apply(event: ReaderRegistered) {
        readers.add(event.transform())
    }

    fun apply(event: BookRegistered) {
        books.add(event.transform())
    }

    fun apply(event: BookBorrowed) {
        books.remove(books.first { it.id == event.bookId } )
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
}