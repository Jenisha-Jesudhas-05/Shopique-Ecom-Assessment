package com.example.shopique.data.local

import com.example.shopique.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        id,
        title,
        price,
        description,
        images = listOf(thumbnail),
        thumbnail,
        category
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id,
        title,
        price,
        description,
        thumbnail,
        category
    )
}