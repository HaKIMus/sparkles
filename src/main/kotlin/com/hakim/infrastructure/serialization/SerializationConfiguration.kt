package com.hakim.infrastructure.serialization

import com.hakim.domain.event.*
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

@Factory
class SerializationConfiguration {
    @Bean
    @Singleton
    fun jsonConfiguration(): Json {
        val domainEventModule = SerializersModule {
            polymorphic(DomainEvent::class) {
                subclass(LibraryInitialized::class, LibraryInitialized.serializer())
                subclass(BookBorrowed::class, BookBorrowed.serializer())
                subclass(BookRegistered::class, BookRegistered.serializer())
                subclass(ReaderRegistered::class, ReaderRegistered.serializer())
            }
            IdKotlinXSerializationModule
        }

        return Json {
            serializersModule = domainEventModule
            ignoreUnknownKeys = true
            isLenient = false
            encodeDefaults = false
        }
    }
}