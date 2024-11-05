package com.tkmrqq.pmsapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.model.CartItem
import com.tkmrqq.pmsapp.data.model.Product

class CartAdapter(private val onRemoveItem: (CartItem) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems = mutableListOf<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
        holder.removeButton.setOnClickListener {
            onRemoveItem(cartItem)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun submitList(items: List<CartItem>) {
        cartItems = items.toMutableList()
        notifyDataSetChanged()
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productNameCart)
        private val productPrice: TextView = itemView.findViewById(R.id.productPriceCart)
        private val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        private val productImage: ImageView = itemView.findViewById(R.id.productImageCart)
        val removeButton: Button = itemView.findViewById(R.id.removeButton)

        fun bind(cartItem: CartItem) {
            productName.text = cartItem.product.name
            productPrice.text = "${cartItem.product.price} BYN"
            productQuantity.text = "Количество: ${cartItem.quantity}"
            // Используйте Glide для загрузки изображения
            Glide.with(itemView.context)
                .load(cartItem.product.imageResId)
                .placeholder(R.drawable.default_image)
                .into(productImage)
        }
    }
}

