package com.example.shopique.presentation.wishlist

import com.example.shopique.domain.model.FavoriteProduct

data class WishlistState(
    val favoriteItems: List<FavoriteProduct> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
