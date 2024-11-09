// CartViewModel.kt
package com.tkmrqq.pmsapp.ui.viewModel

import android.app.Application
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tkmrqq.pmsapp.App
import com.tkmrqq.pmsapp.data.AppDatabase
import com.tkmrqq.pmsapp.data.dao.OrderDao
import com.tkmrqq.pmsapp.data.model.CartItem
import com.tkmrqq.pmsapp.data.model.Order
import com.tkmrqq.pmsapp.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import java.util.UUID

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val db: AppDatabase = (application as App).database
    private val cartDao = db.cartDao()
    private val orderDao = db.orderDao()
    private val productDao = db.productDao()

    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItem>> get() = _cartItems

    private val _orders = MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>> get() = _orders

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    private val _ordersWithItems = MutableLiveData<Map<Order, List<CartItem>>>()
    val ordersWithItems: LiveData<Map<Order, List<CartItem>>> get() = _ordersWithItems


    init {
        loadCartItems()
        loadOrders()
    }

    fun addItem(product: Product) {
        val existingItem = cartDao.getCartItemByProductId(product.id)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartDao.updateCartItem(updatedItem)
        } else {
            val newItem = CartItem(product.id, product.id, quantity = 1)
            cartDao.insertCartItem(newItem)
        }
        updateTotalPrice()
    }


    fun getCartItems(): List<CartItem> {
        return _cartItems.value ?: emptyList()
    }

    fun placeOrder(email: String, phone: String, address: String) {
        val orderId = UUID.randomUUID().toString()
        val orderDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val details = "Email: $email, Phone: $phone"

        val newOrder = Order(orderId, address, orderDate, details)

        viewModelScope.launch {
            orderDao.placeOrder(newOrder, _cartItems.value.orEmpty())
        }

        clearCart()
    }

    private fun updateTotalPrice() {
        viewModelScope.launch {
            val cartItems = _cartItems.value.orEmpty()
            var total = 0.0
            for (cartItem in cartItems) {
                // Получаем продукт из базы данных по productId
                val product = productDao.getProductById(cartItem.productId)
                total += product.price * cartItem.quantity
            }
            _totalPrice.postValue(total)
        }
    }


    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            cartDao.deleteCartItem(cartItem)
            loadCartItems()
        }
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartDao.clearCart()
            loadCartItems()
        }
    }

    private fun loadCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = cartDao.getAllCartItems().toMutableList()
            _cartItems.postValue(items)
            updateTotalPrice()
        }
    }

    private fun loadOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            val allOrders = orderDao.getAllOrders()
            _orders.postValue(allOrders)
        }
    }

    fun loadOrdersWithItems(orderDao: OrderDao) {
        viewModelScope.launch {
            _ordersWithItems.value = orderDao.getAllOrdersWithCartItems()
        }
    }

}

