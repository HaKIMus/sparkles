package com.hakim.domain

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
abstract class Aggregate(val aggregateId: AggregateId)