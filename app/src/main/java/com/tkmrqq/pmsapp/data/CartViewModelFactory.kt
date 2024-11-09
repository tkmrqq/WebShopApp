package com.tkmrqq.pmsapp.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tkmrqq.pmsapp.data.dao.OrderDao
import com.tkmrqq.pmsapp.ui.viewModel.CartViewModel

class CartViewModelFactory(
    private val application: Application,
    private val orderDao: OrderDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(application, orderDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}