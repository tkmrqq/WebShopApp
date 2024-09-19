package com.tkmrqq.pmsapp.ui.screen

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.model.Product
import com.tkmrqq.pmsapp.ui.adapter.ProductAdapter

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val products: List<Product> = listOf(
        Product("Shirt", 25.99),
        Product("Pants", 40.13),
        Product("Jacket", 60.30),
        Product("Hoodie", 60.30)
    )

    AndroidView(
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.home_screen, null)

            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)

            recyclerView.adapter = ProductAdapter(context, products)

            view
        },
        modifier = modifier
    )
}