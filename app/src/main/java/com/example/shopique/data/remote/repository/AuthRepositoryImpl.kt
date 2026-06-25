package com.example.shopique.data.remote.repository

import com.example.shopique.data.local.TokenManager
import com.example.shopique.data.remote.ApiService
import com.example.shopique.data.remote.dto.LoginRequest
import com.example.shopique.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Result<String> {
        return try {
            val response = apiService.login(request)
            tokenManager.saveToken(response.token)
            Result.success(response.token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout() {
        tokenManager.clearToken()
    }

    override fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }
}
