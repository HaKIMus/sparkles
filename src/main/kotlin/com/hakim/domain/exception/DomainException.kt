package com.hakim.domain.exception

sealed class DomainException(message: String) : Exception(message)

class LibraryNotInitializedException(message: String = "Library was not initialized") : DomainException(message)

class UnsupportedEvent(message: String) : DomainException(message)


sealed class EventException(message: String) : DomainException(message)

class AggregateReconstructionException(message: String) : EventException(message)