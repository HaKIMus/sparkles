package com.hakim.infrastructure.broker.model

import kotlinx.serialization.Serializable

@Serializable
data class LibraryInitializedMessage(
    val aggregateId: String
)