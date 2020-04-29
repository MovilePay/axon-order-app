package com.movilepay.aggregate

import java.util.UUID

data class OrderItem(
    val itemId: UUID,
    val name: String,
    val amount: Long
)