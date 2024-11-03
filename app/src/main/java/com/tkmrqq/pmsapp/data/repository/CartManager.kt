package com.tkmrqq.pmsapp.data.repository

import com.tkmrqq.pmsapp.data.model.CartItem
import com.tkmrqq.pmsapp.data.model.Product

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product) {
        val item = cartItems.find { it.product.id == product.id }
        if (item != null) {
            item.quantity++
        } else {
            cartItems.add(CartItem(product))
        }
    }

    fun removeFromCart(product: Product) {
        cartItems.removeAll { it.product.id == product.id }
    }

    fun getCartItems(): List<CartItem> = cartItems
}
