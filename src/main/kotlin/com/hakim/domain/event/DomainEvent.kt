package com.hakim.domain.event

import java.util.UUID

abstract class DomainEvent(val id: UUID) {}