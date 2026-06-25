package com.example.shopique.domain.repository

import com.example.shopique.domain.model.CartItem

interface CartRepository {

    suspend fun getCartItems(): List<CartItem>

    suspend fun addToCart(cartItem: CartItem)

    suspend fun removeFromCart(id: Int)

    suspend fun updateQuantity(id: Int, quantity: Int)

    suspend fun clearCart()
}