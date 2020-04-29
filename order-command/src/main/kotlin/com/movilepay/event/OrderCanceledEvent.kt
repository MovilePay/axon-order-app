package com.movilepay.event

import java.util.UUID

data class OrderCanceledEvent(
    val orderId: UUID,
    val username: String
)