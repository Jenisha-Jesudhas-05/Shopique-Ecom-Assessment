package com.example.shopique.data.repository

import com.example.shopique.data.local.FavoriteDao
import com.example.shopique.data.local.toDomain
import com.example.shopique.data.local.toEntity
import com.example.shopique.domain.model.FavoriteProduct
import com.example.shopique.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override fun getFavoriteItems(): Flow<List<FavoriteProduct>> {
        return favoriteDao.getFavoriteItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addToFavorites(product: FavoriteProduct) {
        favoriteDao.addToFavorites(product.toEntity())
    }

    override suspend fun removeFromFavorites(id: Int) {
        favoriteDao.removeFromFavorites(id)
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return favoriteDao.isFavorite(id)
    }
}
