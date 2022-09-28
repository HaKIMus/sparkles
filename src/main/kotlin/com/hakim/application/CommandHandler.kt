package com.hakim.application

interface CommandHandler<C : Command, T> {
    suspend fun handle(command: C): T
}