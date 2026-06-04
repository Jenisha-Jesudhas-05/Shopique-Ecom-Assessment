package com.example.shopique.presentation.productdetail

import com.example.shopique.domain.model.Product

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String = ""
)