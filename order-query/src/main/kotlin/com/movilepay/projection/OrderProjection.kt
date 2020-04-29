package com.movilepay.projection

import com.movilepay.domain.OrderStatus
import com.movilepay.event.OrderCanceledEvent
import com.movilepay.event.OrderConfirmedEvent
import com.movilepay.event.OrderCreatedEvent
import com.movilepay.event.OrderItemAddedEvent
import com.movilepay.event.OrderItemRemovedEvent
import com.movilepay.model.Order
import com.movilepay.model.OrderItem
import com.movilepay.repository.OrderRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class OrderProjection(private val repository: OrderRepository) {

    @EventHandler
    fun on(event: OrderCreatedEvent) {
        val order = Order(
            id = event.orderId,
            amount = 0L,
            address = event.address,
            customerName = event.customerName,
            status = OrderStatus.CREATED
        )

        repository.save(order)
    }

    @EventHandler
    fun on(event: OrderConfirmedEvent) {
        repository.confirm(event.orderId)
    }

    @EventHandler
    fun on(event: OrderCanceledEvent) {
        repository.cancel(event.orderId, event.username)
    }

    @EventHandler
    fun on(event: OrderItemAddedEvent) {
        val order = repository.findById(event.orderId).get()

        val updatedTotalAmount = order.amount + event.amount
        val updatedOrder = order.copy(amount = updatedTotalAmount)
        updatedOrder.items.add(OrderItem(
            id = event.itemId,
            amount = event.amount,
            name = event.name,
            order = order
        ))

        repository.save(updatedOrder)
    }

    @EventHandler
    fun on(event: OrderItemRemovedEvent) {
        val order = repository.findById(event.orderId).get()
        val item = order.items.first { it.id == event.itemId }

        order.items.remove(item)
        val updatedTotalAmount = order.amount - item.amount
        val updatedOrder = order.copy(amount = updatedTotalAmount)

        repository.save(updatedOrder)
    }
}