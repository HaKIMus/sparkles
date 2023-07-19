package com.hakim.infrastructure

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import jakarta.inject.Named
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Factory
class DispatchersConfiguration {
    @Bean
    @Singleton
    @Named("ioDispatcher")
    fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Bean
    @Singleton
    @Named("mainDispatcher")
    fun mainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}