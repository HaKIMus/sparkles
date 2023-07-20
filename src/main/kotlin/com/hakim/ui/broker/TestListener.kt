package com.hakim.ui.broker

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class TestListener {
    @Topic("test")
    fun receive(@KafkaKey key: String, message: String) {
        println("Received message: $message with key: $key")
    }
}