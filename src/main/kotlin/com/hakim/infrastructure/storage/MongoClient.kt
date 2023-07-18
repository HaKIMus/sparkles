package com.hakim.infrastructure.storage

import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

@Singleton
class MongoClient(@Value("\${mongodb.uri}") private val connection: String) {
    val client: CoroutineClient = KMongo.createClient(connection).coroutine
    val database: CoroutineDatabase = client.getDatabase("sparkles")
}