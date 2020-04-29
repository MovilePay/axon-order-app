package com.movilepay.event

import java.util.UUID

data class OrderConfirmedEvent(
    val orderId: UUID
)