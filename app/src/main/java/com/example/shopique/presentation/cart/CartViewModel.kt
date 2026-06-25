package com.example.shopique.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopique.domain.usecase.GetCartItemsUseCase
import com.example.shopique.domain.usecase.RemoveFromCartUseCase
import com.example.shopique.domain.usecase.UpdateCartQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val items = getCartItemsUseCase()
            _state.update {
                it.copy(
                    cartItems = items,
                    isLoading = false
                )
            }
        }
    }

    fun removeItem(id: Int) {
        viewModelScope.launch {
            removeFromCartUseCase(id)
            loadCartItems()
        }
    }

    fun updateQuantity(id: Int, newQuantity: Int) {
        viewModelScope.launch {
            updateCartQuantityUseCase(id, newQuantity)
            loadCartItems()
        }
    }
}