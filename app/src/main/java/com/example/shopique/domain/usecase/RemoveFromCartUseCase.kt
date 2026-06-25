package com.example.shopique.domain.usecase

import com.example.shopique.domain.repository.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.removeFromCart(id)
    }
}
