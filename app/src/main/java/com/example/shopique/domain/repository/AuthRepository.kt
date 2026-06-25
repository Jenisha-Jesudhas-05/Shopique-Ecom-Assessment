package com.example.shopique.domain.repository

import com.example.shopique.data.remote.dto.LoginRequest

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<String>
    fun logout()
    fun isLoggedIn(): Boolean
}
