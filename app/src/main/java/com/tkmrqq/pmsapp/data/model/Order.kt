package com.tkmrqq.pmsapp.data.model

data class Order (
    val orderId: String,
    val items: List<CartItem>,
    val deliveryAddress: String,
    val orderDate: String
)