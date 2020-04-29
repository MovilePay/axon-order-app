package com.movilepay.api

data class CreateOrderRequest(
    val customerName: String,
    val address: String
)