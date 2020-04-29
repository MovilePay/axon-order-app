package com.movilepay.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "order_item")
data class OrderItem(
    @Id
    val id: UUID,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,
    val name: String,
    val amount: Long
) {

    override fun toString(): String {
        return "OrderItem(id=$id, order=${order.id}, name='$name', amount=$amount)"
    }
}