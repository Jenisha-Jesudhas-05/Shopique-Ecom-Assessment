package com.example.shopique.data.local

import com.example.shopique.domain.model.FavoriteProduct

fun FavoriteEntity.toDomain(): FavoriteProduct {
    return FavoriteProduct(
        id = id,
        title = title,
        price = price,
        image = image,
        category = category
    )
}

fun FavoriteProduct.toEntity(): FavoriteEntity {
    return FavoriteEntity(
        id = id,
        title = title,
        price = price,
        image = image,
        category = category
    )
}
