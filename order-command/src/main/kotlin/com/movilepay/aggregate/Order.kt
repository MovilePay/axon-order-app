package com.movilepay.aggregate

import com.movilepay.command.AddOrderItemCommand
import com.movilepay.command.CancelOrderCommand
import com.movilepay.command.ConfirmOrderCommand
import com.movilepay.command.CreateOrderCommand
import com.movilepay.command.RemoveOrderItemCommand
import com.movilepay.domain.OrderStatus
import com.movilepay.event.OrderCanceledEvent
import com.movilepay.event.OrderConfirmedEvent
import com.movilepay.event.OrderCreatedEvent
import com.movilepay.event.OrderItemAddedEvent
import com.movilepay.event.OrderItemRemovedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.util.Assert
import java.util.UUID

@Aggregate
class Order {

    @AggregateIdentifier
    private lateinit var id: UUID
    private lateinit var customerName: String
    private lateinit var address: String
    private lateinit var status: OrderStatus
    private lateinit var canceledBy: String
    private lateinit var items: MutableList<OrderItem>
    private var amount: Long = 0

    constructor() {}

    @CommandHandler
    constructor(command: CreateOrderCommand) {
        apply(OrderCreatedEvent(
            orderId = command.orderId,
            customerName = command.customerName,
            address = command.address
        ))
    }

    @CommandHandler
    fun handle(command: ConfirmOrderCommand) {
        if (OrderStatus.CANCELED == status) {
            throw IllegalStateException("Order is canceled")
        }
        
        apply(OrderConfirmedEvent(
            orderId = command.orderId
        ))
    }

    @CommandHandler
    fun handle(command: CancelOrderCommand) {
        apply(OrderCanceledEvent(
            orderId = command.orderId,
            username = command.username
        ))
    }

    @CommandHandler
    fun handle(command: AddOrderItemCommand) {
        apply(OrderItemAddedEvent(
            itemId = UUID.randomUUID(),
            orderId = command.orderId,
            name = command.name,
            amount = command.amount
        ))
    }

    @CommandHandler
    fun handle(command: RemoveOrderItemCommand) {
        val item = items.firstOrNull { it.itemId == command.itemId }
        Assert.notNull(item, "Item does not exist")

        apply(OrderItemRemovedEvent(
            itemId = command.itemId,
            orderId = command.orderId
        ))
    }

    @EventSourcingHandler
    fun on(event: OrderCreatedEvent) {
        id = event.orderId
        customerName = event.customerName
        address = event.address
        status = OrderStatus.CREATED
        items = mutableListOf()
    }

    @EventSourcingHandler
    fun on(event: OrderConfirmedEvent) {
        status = OrderStatus.CONFIRMED
    }

    @EventSourcingHandler
    fun on(event: OrderCanceledEvent) {
        status = OrderStatus.CANCELED
        canceledBy = event.username
    }

    @EventSourcingHandler
    fun on(event: OrderItemAddedEvent) {
        val item = OrderItem(
            itemId = event.itemId,
            amount = event.amount,
            name = event.name
        )

        amount += event.amount

        items.add(item)
    }

    @EventSourcingHandler
    fun on(event: OrderItemRemovedEvent) {
        items.removeIf { it.itemId == event.itemId }
    }
}