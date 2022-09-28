package com.hakim.application

interface UnitCommandHandler<C : Command> : CommandHandler<C, Unit>