package com.tkmrqq.pmsapp.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.model.Product
import com.tkmrqq.pmsapp.ui.adapter.ProductAdapter

class HomeFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val products: List<Product> = listOf(
            Product(1,"Shirt", 25.99, "qweqwe", R.drawable.yoru),
            Product(2,"Pants", 40.13, "desc", R.drawable.yoru),
            Product(3,"Jacket", 60.30, "desc", R.drawable.yoru),
            Product(4,"Hoodie", 60.30, "desc", R.drawable.yoru),
            Product(5,"T-Shirt", 69.30, "beautiful cotton t-shirt", R.drawable.yoru),
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ProductAdapter(requireContext(), products)
        Log.d("HomeFragment", "Number of products ${products.size}")
        Log.d("HomeFragment", "RecyclerView adapter set")

    }
}