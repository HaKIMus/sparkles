package com.hakim.infrastructure.storage

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import org.litote.kmongo.KMongo

@Singleton
class MongoClient(@Value("\${mongodb.uri}") private val connection: String) {
    val client: MongoClient = KMongo.createClient(connection)
    val database: MongoDatabase = client.getDatabase("sparkles")
}