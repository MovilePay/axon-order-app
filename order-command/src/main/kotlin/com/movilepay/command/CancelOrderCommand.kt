package com.movilepay.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class CancelOrderCommand(
    @TargetAggregateIdentifier
    val orderId: UUID,
    val username: String
)