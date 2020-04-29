package com.movilepay.webservice

import com.movilepay.api.AddOrderItemRequest
import com.movilepay.command.AddOrderItemCommand
import com.movilepay.command.RemoveOrderItemCommand
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
class OrderItemController(commandBus: CommandBus) {

    private val commandGateway = DefaultCommandGateway
        .builder()
        .commandBus(commandBus)
        .build()

    @PostMapping("/orders/{orderId}/items")
    fun confirm(@PathVariable orderId: UUID, @RequestBody request: AddOrderItemRequest): ResponseEntity<Void> {
        val command = AddOrderItemCommand(
            orderId = orderId,
            name = request.name,
            amount = request.amount
        )

        commandGateway.sendAndWait<AddOrderItemCommand>(command)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/orders/{orderId}/items/{id}")
    fun cancel(@PathVariable orderId: UUID, @PathVariable id: UUID): ResponseEntity<Void> {
        val command = RemoveOrderItemCommand(
            orderId = orderId,
            itemId = id
        )

        commandGateway.sendAndWait<RemoveOrderItemCommand>(command)

        return ResponseEntity.ok().build()
    }
}