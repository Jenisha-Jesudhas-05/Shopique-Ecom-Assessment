package com.example.shopique.data.local

import com.example.shopique.domain.model.CartItem

fun CartItemEntity.toDomain() = CartItem(
    id,
    title,
    price,
    image,
    quantity
)

fun CartItem.toEntity() = CartItemEntity(
    id,
    title,
    price,
    image,
    quantity
)