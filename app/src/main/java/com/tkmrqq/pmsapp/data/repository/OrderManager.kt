package com.tkmrqq.pmsapp.data.repository

import com.tkmrqq.pmsapp.data.model.Order

object OrderManager {
    private val orders = mutableListOf<Order>()

    fun addOrder(order: Order) {
        orders.add(order)
    }

    fun getOrders(): List<Order> = orders
}
