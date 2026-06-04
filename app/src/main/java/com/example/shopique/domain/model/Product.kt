package com.example.shopique.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val images: List<String>,
    val thumbnail: String,
    val category: String
)