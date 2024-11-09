package com.tkmrqq.pmsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tkmrqq.pmsapp.data.dao.CartDao
import com.tkmrqq.pmsapp.data.dao.OrderDao
import com.tkmrqq.pmsapp.data.dao.ProductDao
import com.tkmrqq.pmsapp.data.model.CartItem
import com.tkmrqq.pmsapp.data.model.Order
import com.tkmrqq.pmsapp.data.model.OrderItemCrossRef
import com.tkmrqq.pmsapp.data.model.Product

@Database(entities = [Product::class, CartItem::class, Order::class, OrderItemCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun productDao(): ProductDao
}