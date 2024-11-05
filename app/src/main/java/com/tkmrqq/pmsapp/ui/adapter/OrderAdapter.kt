package com.tkmrqq.pmsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tkmrqq.pmsapp.R
import com.tkmrqq.pmsapp.data.model.Order

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.orderTitle.text = "Заказ ${position + 1}"
        holder.orderDetails.text = order.details

        // Инициализация вложенного адаптера CartItemAdapter
        val cartItemAdapter = CartItemAdapter(order.items)
        holder.cartItemsRecyclerView.adapter = cartItemAdapter
        holder.cartItemsRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

    override fun getItemCount(): Int = orders.size

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderTitle: TextView = itemView.findViewById(R.id.orderTitle)
        val orderDetails: TextView = itemView.findViewById(R.id.orderDetails)
        val cartItemsRecyclerView: RecyclerView = itemView.findViewById(R.id.cartItemsRecyclerView)
    }
}
