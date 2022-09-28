package com.hakim.application.command

import com.hakim.application.Command
import com.hakim.domain.AggregateId
import com.hakim.domain.Book

class RegisterBookCommand(val libraryId: AggregateId, val book: Book) : Command {
}