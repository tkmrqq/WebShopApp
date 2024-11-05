package com.tkmrqq.pmsapp.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.model.Product
import com.tkmrqq.pmsapp.ui.adapter.CartAdapter
import com.tkmrqq.pmsapp.ui.adapter.CartItemAdapter
import com.tkmrqq.pmsapp.ui.adapter.ProductAdapter
import com.tkmrqq.pmsapp.ui.viewModel.CartViewModel

class CartFragment : Fragment() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartItemAdapter: CartItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cart_screen, container, false)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        cartAdapter = CartAdapter { cartItem ->
            cartViewModel.removeItem(cartItem)
        }
        cartItemAdapter = CartItemAdapter(cartViewModel.getCartItems())

        val recyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = cartAdapter

        val emailInput = view.findViewById<EditText>(R.id.emailInput)
        val phoneInput = view.findViewById<EditText>(R.id.phoneInput)
        val addressInput = view.findViewById<EditText>(R.id.addressInput)
        val orderButton = view.findViewById<Button>(R.id.orderButton)

        orderButton.setOnClickListener {
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()
            val address = addressInput.text.toString()
            val cartItems = cartViewModel.getCartItems()

            if (email.isNotBlank() && phone.isNotBlank() && address.isNotBlank()) {
                cartViewModel.placeOrder(email, phone, address)
                Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_LONG).show()
                cartViewModel.clearCart()
            } else {
                Toast.makeText(requireContext(), "Please fill in all details and add items to cart", Toast.LENGTH_SHORT).show()
            }
        }


        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            Log.d("CartFragment", "Observed cart items: $cartItems")
            cartAdapter.submitList(cartItems.toList()) // обновление данных в адаптере
        }
        return view
    }
}