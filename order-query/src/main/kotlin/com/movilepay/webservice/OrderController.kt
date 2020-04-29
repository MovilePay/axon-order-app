package com.movilepay.webservice

import com.movilepay.domain.OrderStatus
import com.movilepay.model.Order
import com.movilepay.repository.OrderRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
class OrderController(val repository: OrderRepository) {

    @GetMapping("/orders")
    fun findAll(@RequestParam(value = "status", required = false) status: OrderStatus? = null): ResponseEntity<List<Order>> {
        val orders = if (status == null) {
            repository.findAll()
        } else {
            repository.findAllByStatus(status)
        }

        return ResponseEntity.ok(orders)
    }

    @GetMapping("/orders/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Order> {
        val order = repository.findById(id).orElseThrow { IllegalStateException("Order does not exist") }
        return ResponseEntity.ok(order)
    }
}