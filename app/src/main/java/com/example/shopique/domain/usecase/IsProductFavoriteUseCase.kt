package com.example.shopique.domain.usecase

import com.example.shopique.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsProductFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.isFavorite(id)
    }
}
