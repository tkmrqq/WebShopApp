package com.tkmrqq.pmsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.dao.ProductDao
import com.tkmrqq.pmsapp.data.model.CartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartItemAdapter(
    private val productDao: ProductDao
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    private var cartItems = mutableListOf<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_item, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int = cartItems.size

    fun submitList(items: List<CartItem>) {
        cartItems = items.toMutableList()
        notifyDataSetChanged()
    }

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)

        fun bind(cartItem: CartItem) {
            CoroutineScope(Dispatchers.Main).launch {
                val product = withContext(Dispatchers.IO) {
                    productDao.getProductById(cartItem.productId)
                }
                product?.let {
                    productName.text = it.name
                    Glide.with(itemView.context)
                        .load(it.imageResId)
                        .placeholder(R.drawable.default_image)
                        .into(productImageView)
                }
            }
        }
    }
}
