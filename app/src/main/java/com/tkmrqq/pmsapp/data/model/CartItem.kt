package com.tkmrqq.pmsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "cart_items",
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class CartItem (
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val productId: Int,
    var quantity: Int = 1
)