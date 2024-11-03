package com.tkmrqq.pmsapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.model.Product
import com.tkmrqq.pmsapp.ui.screen.ProductDetailFragment

class ProductAdapter(private val context: Context, private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.productImage)
        val productName: TextView = view.findViewById(R.id.productName)
        val productPrice: TextView = view.findViewById(R.id.productPrice)
        val productDescription: TextView = view.findViewById(R.id.productDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.productName.text = product.name
        holder.productPrice.text = "${product.price} BYN"
        holder.productDescription.text = product.description

        when (product.imageUrl){
            is String -> Glide.with(context).load(product.imageUrl).into(holder.productImage)
            is Int -> Glide.with(context).load(product.imageUrl).into(holder.productImage)
        }

        Log.d("ProductAdapter", "Binding product: ${product.name}")

        holder.itemView.setOnClickListener {
            val fragment = ProductDetailFragment.newInstance(
                product.name,
                product.price,
                product.description,
                product.imageUrl.toString()
            )
            val activity = holder.itemView.context as AppCompatActivity
            activity.findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE
            activity.findViewById<RecyclerView>(R.id.recyclerView).visibility = View.GONE
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
    override fun getItemCount(): Int = products.size
}