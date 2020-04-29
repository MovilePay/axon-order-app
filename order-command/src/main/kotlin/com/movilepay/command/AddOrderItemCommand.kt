package com.movilepay.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class AddOrderItemCommand(
    @TargetAggregateIdentifier
    val orderId: UUID,
    val name: String,
    val amount: Long
)