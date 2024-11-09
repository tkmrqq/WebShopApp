package com.tkmrqq.pmsapp.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tkmrqq.pmsapp.App
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.CartViewModelFactory
import com.tkmrqq.pmsapp.data.dao.OrderDao
import com.tkmrqq.pmsapp.data.dao.ProductDao
import com.tkmrqq.pmsapp.data.model.Product
import com.tkmrqq.pmsapp.ui.adapter.CartAdapter
import com.tkmrqq.pmsapp.ui.adapter.CartItemAdapter
import com.tkmrqq.pmsapp.ui.adapter.ProductAdapter
import com.tkmrqq.pmsapp.ui.viewModel.CartViewModel

class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var orderDao: OrderDao
    private lateinit var productDao: ProductDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cart_screen, container, false)

        // Получаем экземпляры orderDao и productDao из базы данных
        val database = (requireContext().applicationContext as App).database
        val application = requireActivity().application as App
        orderDao = database.orderDao()
        productDao = database.productDao()

        // Используем фабрику для создания экземпляра CartViewModel
        val factory = CartViewModelFactory(application, orderDao)
        cartViewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)

        // Инициализируем адаптер с productDao
        cartAdapter = CartAdapter(productDao) { cartItem ->
            cartViewModel.removeItem(cartItem)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = cartAdapter

        // Установка обработчика для кнопки оформления заказа
        val orderButton = view.findViewById<Button>(R.id.orderButton)
        orderButton.setOnClickListener {
            placeOrder()
        }

        // Обновление данных корзины
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.submitList(cartItems.toList())
        }

        // Обновление цены
        val totalPriceTextView = view.findViewById<TextView>(R.id.sumPrice)
        cartViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            totalPriceTextView.text = "Итого: ${"%.2f".format(totalPrice)} BYN"
        }

        return view
    }

    private fun placeOrder() {
        // Логика оформления заказа
    }
}

