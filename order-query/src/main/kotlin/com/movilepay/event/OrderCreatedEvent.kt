package com.movilepay.event

import java.util.UUID

data class OrderCreatedEvent(
    val orderId: UUID,
    val customerName: String,
    val address: String
)