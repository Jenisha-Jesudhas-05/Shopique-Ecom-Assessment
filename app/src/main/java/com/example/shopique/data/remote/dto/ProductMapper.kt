package com.example.shopique.data.remote.dto

import com.example.shopique.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        images = listOf(image),
        thumbnail = image,
        category = category
    )
}
