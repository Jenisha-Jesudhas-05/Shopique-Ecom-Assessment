package com.example.shopique.domain.usecase

import com.example.shopique.domain.model.FavoriteProduct
import com.example.shopique.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<FavoriteProduct>> {
        return repository.getFavoriteItems()
    }
}
