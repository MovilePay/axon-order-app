package com.movilepay.event

import java.util.UUID

data class OrderItemRemovedEvent(
    val orderId: UUID,
    val itemId: UUID
)