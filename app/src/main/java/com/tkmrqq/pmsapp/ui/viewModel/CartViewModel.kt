// CartViewModel.kt
package com.tkmrqq.pmsapp.ui.viewModel

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tkmrqq.pmsapp.data.model.CartItem
import com.tkmrqq.pmsapp.data.model.CartLibrary
import com.tkmrqq.pmsapp.data.model.Order
import com.tkmrqq.pmsapp.data.model.Product
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.random.Random

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItem>> get() = _cartItems

    private val _orders = MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>> get() = _orders

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun addItem(product: Product) {
        val existingItem = _cartItems.value?.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            _cartItems.value?.add(CartItem(product))
        }
        updateTotalPrice()
        _cartItems.notifyObserver()
    }

    fun getCartItems(): List<CartItem> {
        return _cartItems.value ?: emptyList()
    }

    fun placeOrder(email: String, phone: String, address: String) {
        val orderId = UUID.randomUUID().toString()
        val orderDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val details = "Email: $email, Phone: $phone"
        val newOrder = Order(orderId, _cartItems.value.orEmpty(), address, orderDate, details)
        _orders.value = _orders.value.orEmpty() + newOrder
        clearCart()
    }

    private fun updateTotalPrice() {
        val cartItems = _cartItems.value?.map { "${it.product.id},${it.product.name},${it.quantity},${it.product.price}" }?.toTypedArray() ?: emptyArray()
        val total = CartLibrary().calculateTotalPrice(cartItems)
        _totalPrice.value = total
    }

    fun removeItem(cartItem: CartItem) {
        _cartItems.value = _cartItems.value?.filter { it != cartItem }?.toMutableList()
        updateTotalPrice()
        _cartItems.notifyObserver()
    }

    fun clearCart() {
        _cartItems.value = mutableListOf()
        updateTotalPrice()
        _cartItems.notifyObserver()
    }

    private fun MutableLiveData<MutableList<CartItem>>.notifyObserver() {
        this.value = this.value
    }

    fun saveCartToFile(filename: String) {
        val cartItemsArray = getCartItems().toTypedArray()
        CartLibrary().saveCartToFile(cartItemsArray, cartItemsArray.size, filename)
    }

    fun loadCartFromFile(filename: String) {
        val maxItems = 100
        val id: Int = 0
        val loadedItems = Array(maxItems) { CartItem(Product(id, "", 0.0), 0) }
        val itemCount = CartLibrary().loadCartFromFile(loadedItems, maxItems, filename)

        _cartItems.value = loadedItems.take(itemCount).toMutableList()
        updateTotalPrice()
        _cartItems.notifyObserver()
    }

}

