package com.example.shopique.domain.usecase

import com.example.shopique.domain.model.FavoriteProduct
import com.example.shopique.domain.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(product: FavoriteProduct) {
        if (repository.isFavorite(product.id)) {
            repository.removeFromFavorites(product.id)
        } else {
            repository.addToFavorites(product)
        }
    }
}
