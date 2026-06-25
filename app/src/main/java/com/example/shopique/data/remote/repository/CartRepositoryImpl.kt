package com.example.shopique.data.remote.repository


import com.example.shopique.data.local.CartDao
import com.example.shopique.data.local.toDomain
import com.example.shopique.data.local.toEntity
import com.example.shopique.domain.model.CartItem
import com.example.shopique.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override suspend fun getCartItems(): List<CartItem> {
        return cartDao.getCartItems().map { it.toDomain() }
    }

    override suspend fun addToCart(cartItem: CartItem) {
        cartDao.addToCart(cartItem.toEntity())
    }

    override suspend fun removeFromCart(id: Int) {
        cartDao.removeFromCart(id)
    }

    override suspend fun updateQuantity(id: Int, quantity: Int) {
        cartDao.updateQuantity(id, quantity)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}