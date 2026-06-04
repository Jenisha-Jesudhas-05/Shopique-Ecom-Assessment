package com.example.shopique.data.remote.repository

import com.example.shopique.data.remote.ApiService
import com.example.shopique.data.remote.dto.toDomain
import com.example.shopique.domain.model.Product
import com.example.shopique.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            val products = apiService.getProducts().map { it.toDomain() }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            val product = apiService.getProductById(id).toDomain()
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}