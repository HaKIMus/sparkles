package com.hakim.domain

import com.hakim.infrastructure.serialization.UuidSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Reader(@Serializable(with = UuidSerializer::class) val id: UUID) {
}