package com.hakim.ui.broker

import com.hakim.application.UnitCommandHandler
import com.hakim.application.command.InitializeLibraryBookCommand
import com.hakim.domain.AggregateId
import com.hakim.infrastructure.broker.model.LibraryInitializedMessage
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Inject
import org.slf4j.LoggerFactory.getLogger

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class InitializeLibraryListener @Inject constructor(
    private val handler: UnitCommandHandler<InitializeLibraryBookCommand>
) {
    private val logger = getLogger(InitializeLibraryListener::class.java)
    @Topic("initialize-library")
    suspend fun receive(library: LibraryInitializedMessage) {
        logger.info("Received message to initialize library with id: ${library.aggregateId}")

        handler.handle(InitializeLibraryBookCommand(AggregateId.fromString(library.aggregateId)))
    }
}