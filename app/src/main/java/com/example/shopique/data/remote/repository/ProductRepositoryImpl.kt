package com.example.shopique.data.remote.repository

import com.example.shopique.data.local.ProductDao
import com.example.shopique.data.local.toDomain
import com.example.shopique.data.local.toEntity
import com.example.shopique.data.remote.ApiService
import com.example.shopique.data.remote.dto.toDomain
import com.example.shopique.domain.model.Product
import com.example.shopique.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {

            // Fetch from API
            val products = apiService
                .getProducts()
                .map { it.toDomain() }

            // Save to Room
            productDao.insertProducts(
                products.map { it.toEntity() }
            )

            Result.success(products)

        } catch (e: Exception) {

            // Load from Room if API fails
            val cachedProducts = productDao
                .getProducts()
                .map { it.toDomain() }

            if (cachedProducts.isNotEmpty()) {
                Result.success(cachedProducts)
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {

            val product = apiService
                .getProductById(id)
                .toDomain()

            Result.success(product)

        } catch (e: Exception) {

            productDao.getProductById(id)?.let {
                return Result.success(it.toDomain())
            }

            Result.failure(e)
        }
    }
}