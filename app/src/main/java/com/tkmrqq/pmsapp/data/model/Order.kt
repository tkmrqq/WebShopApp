package com.tkmrqq.pmsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order (
    @PrimaryKey val orderId: String,
    val deliveryAddress: String,
    val orderDate: String,
    val details: String
)

@Entity(
    tableName = "order_items",
    primaryKeys = ["orderId", "cartItemId"]
)
data class OrderItemCrossRef(
    val orderId: String,
    val cartItemId: Int
)