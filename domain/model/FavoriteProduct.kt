package com.example.shopique.domain.model

data class FavoriteProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    val category: String
)
