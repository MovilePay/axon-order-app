package com.movilepay.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class RemoveOrderItemCommand(
    @TargetAggregateIdentifier
    val orderId: UUID,
    val itemId: UUID
)