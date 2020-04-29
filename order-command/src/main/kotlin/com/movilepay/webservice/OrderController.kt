package com.movilepay.webservice

import com.movilepay.api.CancelOrderRequest
import com.movilepay.api.CreateOrderRequest
import com.movilepay.command.CancelOrderCommand
import com.movilepay.command.ConfirmOrderCommand
import com.movilepay.command.CreateOrderCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
class OrderController(private val commandGateway: CommandGateway) {

    @PostMapping("/orders")
    fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<CreateOrderCommand> {
        val command = CreateOrderCommand(
            orderId = UUID.randomUUID(),
            address = request.address,
            customerName = request.customerName
        )

        return ResponseEntity.ok(commandGateway.sendAndWait(command))
    }

    @PostMapping("/orders/{id}/confirmations")
    fun confirm(@PathVariable id: UUID): ResponseEntity<ConfirmOrderCommand> {
        val command = ConfirmOrderCommand(
            orderId = id
        )

        return ResponseEntity.ok(commandGateway.sendAndWait(command))
    }

    @DeleteMapping("/orders/{id}")
    fun cancel(@PathVariable id: UUID, @RequestBody request: CancelOrderRequest): ResponseEntity<CancelOrderCommand> {
        val command = CancelOrderCommand(
            orderId = id,
            username = request.username
        )

        return ResponseEntity.ok(commandGateway.sendAndWait(command))
    }
}