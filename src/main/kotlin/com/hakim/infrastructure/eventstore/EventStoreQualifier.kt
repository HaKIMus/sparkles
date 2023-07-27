package com.hakim.infrastructure.eventstore

import jakarta.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CachedEventStoreQualifier()

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EventStoreQualifier()
