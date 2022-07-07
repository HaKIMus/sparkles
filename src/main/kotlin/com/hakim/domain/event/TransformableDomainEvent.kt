package com.hakim.domain.event

interface TransformableDomainEvent<T> {
    fun transform(): T
}