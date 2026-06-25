package com.example.shopique.domain.repository

import com.example.shopique.domain.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteItems(): Flow<List<FavoriteProduct>>
    suspend fun addToFavorites(product: FavoriteProduct)
    suspend fun removeFromFavorites(id: Int)
    suspend fun isFavorite(id: Int): Boolean
}
