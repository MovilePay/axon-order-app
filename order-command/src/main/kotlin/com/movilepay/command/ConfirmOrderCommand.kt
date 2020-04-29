package com.movilepay.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class ConfirmOrderCommand(
    @TargetAggregateIdentifier
    val orderId: UUID
)