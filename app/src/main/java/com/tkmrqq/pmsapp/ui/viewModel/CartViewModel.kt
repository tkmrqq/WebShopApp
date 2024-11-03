// CartViewModel.kt
package com.tkmrqq.pmsapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tkmrqq.pmsapp.data.model.Product

class CartViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<MutableList<Product>>(mutableListOf())
    val cartItems: LiveData<MutableList<Product>> = _cartItems

    fun addItem(product: Product) {
        _cartItems.value?.add(product)
        _cartItems.value = _cartItems.value // Обновление LiveData
    }

    fun removeItem(product: Product) {
        _cartItems.value?.remove(product)
        _cartItems.value = _cartItems.value // Обновление LiveData
    }

    fun clearCart() {
        _cartItems.value?.clear()
        _cartItems.value = _cartItems.value
    }
}
