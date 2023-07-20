package com.hakim.infrastructure.broker.producer

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface TestClient {
    @Topic
    fun send(@Topic topic: String, @KafkaKey key: String, message: String)
}