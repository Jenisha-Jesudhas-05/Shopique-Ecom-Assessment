package com.example.shopique.presentation.productlist

import com.example.shopique.domain.model.Product

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)