package com.example.shopique.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopique.data.remote.dto.LoginRequest
import com.example.shopique.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        _state.update { it.copy(isLoggedIn = authRepository.isLoggedIn()) }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "") }
            authRepository.login(LoginRequest(username, password)).fold(
                onSuccess = {
                    _state.update { it.copy(isLoading = false, isLoggedIn = true) }
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, error = error.message ?: "Login failed") }
                }
            )
        }
    }

    fun logout() {
        authRepository.logout()
        _state.update { it.copy(isLoggedIn = false) }
    }
}
