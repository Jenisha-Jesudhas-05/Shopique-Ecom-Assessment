package com.example.shopique.presentation.auth

data class AuthState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoggedIn: Boolean = false
)
