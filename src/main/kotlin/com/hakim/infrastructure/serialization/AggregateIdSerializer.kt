package com.hakim.infrastructure.serialization

import com.hakim.domain.AggregateId
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

object AggregateIdSerializer : KSerializer<AggregateId> {
    override val descriptor = PrimitiveSerialDescriptor("AggregateId", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): AggregateId {
        return AggregateId.fromUuid(UUID.fromString(decoder.decodeString()))
    }

    override fun serialize(encoder: Encoder, value: AggregateId) {
        encoder.encodeString(value.id.toString())
    }
}