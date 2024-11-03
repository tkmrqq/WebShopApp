package com.tkmrqq.pmsapp.data.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String = "",
    val imageUrl: Any
)
