package com.movilepay.event

import java.util.UUID

data class OrderItemAddedEvent(
    val orderId: UUID,
    val itemId: UUID,
    val name: String,
    val amount: Long
)