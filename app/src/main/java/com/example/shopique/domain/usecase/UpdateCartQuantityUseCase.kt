package com.example.shopique.domain.usecase

import com.example.shopique.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(id: Int, quantity: Int) {
        if (quantity > 0) {
            repository.updateQuantity(id, quantity)
        } else {
            repository.removeFromCart(id)
        }
    }
}
