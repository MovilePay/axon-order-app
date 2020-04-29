package com.movilepay.repository

import com.movilepay.domain.OrderStatus
import com.movilepay.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface OrderRepository : JpaRepository<Order, UUID> {

    @Modifying
    @Query("update Order o set o.status = 'CONFIRMED' where o.id = ?1")
    fun confirm(id: UUID)

    @Modifying
    @Query("update Order o set o.status = 'CANCELED', o.canceledBy = ?2 where o.id = ?1")
    fun cancel(id: UUID, username: String)

    fun findAllByStatus(status: OrderStatus): List<Order>
}