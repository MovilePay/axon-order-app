package com.movilepay.webservice

import com.movilepay.api.CancelOrderRequest
import com.movilepay.api.CreateOrderRequest
import com.movilepay.command.CancelOrderCommand
import com.movilepay.command.ConfirmOrderCommand
import com.movilepay.command.CreateOrderCommand
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
class OrderController(commandBus: CommandBus) {

    private val commandGateway = DefaultCommandGateway
        .builder()
        .commandBus(commandBus)
        .build()

    @PostMapping("/orders")
    fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<Void> {
        val command = CreateOrderCommand(
            orderId = UUID.randomUUID(),
            address = request.address,
            customerName = request.customerName
        )

        commandGateway.sendAndWait<CreateOrderCommand>(command)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/orders/{id}/confirmations")
    fun confirm(@PathVariable id: UUID): ResponseEntity<Void> {
        val command = ConfirmOrderCommand(
            orderId = id
        )

        commandGateway.sendAndWait<ConfirmOrderCommand>(command)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/orders/{id}")
    fun cancel(@PathVariable id: UUID, @RequestBody request: CancelOrderRequest): ResponseEntity<Void> {
        val command = CancelOrderCommand(
            orderId = id,
            username = request.username
        )

        commandGateway.sendAndWait<ConfirmOrderCommand>(command)

        return ResponseEntity.ok().build()
    }
}