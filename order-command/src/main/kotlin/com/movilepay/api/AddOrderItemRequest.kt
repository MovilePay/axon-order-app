package com.movilepay.api

data class AddOrderItemRequest(
    val name: String,
    val amount: Long
)