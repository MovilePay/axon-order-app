package com.movilepay.model

import com.movilepay.domain.OrderStatus
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "orders")
data class Order(
    @Id
    val id: UUID,
    val customerName: String,
    val address: String,
    @Enumerated(EnumType.STRING)
    val status: OrderStatus,
    val amount: Long,
    val canceledBy: String? = null,
    @OneToMany(
        mappedBy = "order",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val items: MutableList<OrderItem> = mutableListOf()
)