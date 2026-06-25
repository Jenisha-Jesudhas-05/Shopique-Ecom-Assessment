package com.example.shopique.presentation.cart

import com.example.shopique.domain.model.CartItem

data class CartState(
    val cartItems: List<CartItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
