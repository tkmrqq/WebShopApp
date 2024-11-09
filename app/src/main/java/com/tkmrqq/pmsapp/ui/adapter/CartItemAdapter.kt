package com.tkmrqq.pmsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.model.CartItem

class CartItemAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_item, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = cartItems[position]
        cartItem.product.imageResId?.let { holder.productImageView.setImageResource(it) }
        holder.productName.text = cartItem.product.name
    }

    override fun getItemCount(): Int = cartItems.size

    class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
    }
}
