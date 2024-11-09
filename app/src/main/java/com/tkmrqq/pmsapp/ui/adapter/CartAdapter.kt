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
import com.tkmrqq.pmsapp.data.dao.ProductDao
import com.tkmrqq.pmsapp.data.model.CartItem
import com.tkmrqq.pmsapp.data.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(
    private val productDao: ProductDao,
    private val onRemoveItem: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

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

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productNameCart)
        private val productPrice: TextView = itemView.findViewById(R.id.productPriceCart)
        private val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        private val productImage: ImageView = itemView.findViewById(R.id.productImageCart)
        val removeButton: Button = itemView.findViewById(R.id.removeButton)

        fun bind(cartItem: CartItem) {
            productQuantity.text = "Количество: ${cartItem.quantity}"

            // Загружаем данные продукта по productId асинхронно
            CoroutineScope(Dispatchers.Main).launch {
                val product = withContext(Dispatchers.IO) {
                    productDao.getProductById(cartItem.productId)
                }
                product?.let {
                    productName.text = it.name
                    productPrice.text = "${it.price} BYN"
                    Glide.with(itemView.context)
                        .load(it.imageResId)
                        .placeholder(R.drawable.default_image)
                        .into(productImage)
                }
            }
        }
    }
}

