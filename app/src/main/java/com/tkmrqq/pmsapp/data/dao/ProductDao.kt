package com.tkmrqq.pmsapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.tkmrqq.pmsapp.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: Int): Product
}