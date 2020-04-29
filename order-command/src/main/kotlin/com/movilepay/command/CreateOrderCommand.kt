package com.movilepay.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class CreateOrderCommand(
    @TargetAggregateIdentifier
    val orderId: UUID,
    val customerName: String,
    val address: String
)