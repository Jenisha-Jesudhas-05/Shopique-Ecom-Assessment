package com.example.shopique.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int
)