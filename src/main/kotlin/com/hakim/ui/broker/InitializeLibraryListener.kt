package com.hakim.ui.broker

import com.hakim.application.command.InitializeLibraryBookCommand
import com.hakim.application.command.InitializeLibraryBookCommandHandler
import com.hakim.domain.AggregateId
import com.hakim.infrastructure.broker.model.LibraryInitializedMessage
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory.getLogger

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class InitializeLibraryListener @Inject constructor(
    private val handler: InitializeLibraryBookCommandHandler
) {
    private val logger = getLogger(InitializeLibraryListener::class.java)
    @Topic("initialize-library")
    fun receive(library: LibraryInitializedMessage) = runBlocking {
        logger.info("Received message to initialize library with id: ${library.aggregateId}")

        handler.handle(InitializeLibraryBookCommand(AggregateId.fromString(library.aggregateId)))
    }
}