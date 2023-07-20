package com.hakim.application.command

import com.hakim.application.Command
import com.hakim.domain.AggregateId

data class InitializeLibraryBookCommand(
    val aggregateId: AggregateId
) : Command