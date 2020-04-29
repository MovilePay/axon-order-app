package com.movilepay.webservice

import com.movilepay.api.AddOrderItemRequest
import com.movilepay.command.AddOrderItemCommand
import com.movilepay.command.RemoveOrderItemCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
class OrderItemController(private val commandGateway: CommandGateway) {

    @PostMapping("/orders/{orderId}/items")
    fun confirm(@PathVariable orderId: UUID, @RequestBody request: AddOrderItemRequest): ResponseEntity<AddOrderItemCommand> {
        val command = AddOrderItemCommand(
            orderId = orderId,
            name = request.name,
            amount = request.amount
        )

        return ResponseEntity.ok(commandGateway.sendAndWait(command))
    }

    @DeleteMapping("/orders/{orderId}/items/{id}")
    fun cancel(@PathVariable orderId: UUID, @PathVariable id: UUID): ResponseEntity<RemoveOrderItemCommand> {
        val command = RemoveOrderItemCommand(
            orderId = orderId,
            itemId = id
        )

        return ResponseEntity.ok(commandGateway.sendAndWait(command))
    }
}