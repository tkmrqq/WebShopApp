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
            Product(1,"Shirt", 49.00, "qweqwe", R.drawable.yoru),
            Product(2,"Jeans", 39.00, "desc", R.drawable.jeans),
            Product(3,"Batnik", 60.00, "desc", R.drawable.batnik),
            Product(4,"LongSleeve", 60.00, "desc", R.drawable.longsleeve),
            Product(5,"Shirt", 59.00, "t-shirt with Makima art from Chainsaw-Man", R.drawable.makima),
            Product(6,"Batnik", 99.00, "batnik with Makima from CSM", R.drawable.makimabatnik),
            Product(7,"T-Shirt", 69.00, "Hoshino Ai from Oshi no ko", R.drawable.hoshino),
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ProductAdapter(requireContext(), products)
        Log.d("HomeFragment", "Number of products ${products.size}")
        Log.d("HomeFragment", "RecyclerView adapter set")

    }
}