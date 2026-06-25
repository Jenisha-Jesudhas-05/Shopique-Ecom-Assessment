package com.example.shopique.domain.usecase

import com.example.shopique.domain.model.CartItem
import com.example.shopique.domain.repository.CartRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(): List<CartItem> {
        return repository.getCartItems()
    }
}
