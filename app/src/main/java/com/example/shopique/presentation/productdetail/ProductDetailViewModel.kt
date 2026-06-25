package com.example.shopique.presentation.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopique.domain.model.CartItem
import com.example.shopique.domain.model.FavoriteProduct
import com.example.shopique.domain.usecase.AddToCartUseCase
import com.example.shopique.domain.usecase.GetProductByIdUseCase
import com.example.shopique.domain.usecase.IsProductFavoriteUseCase
import com.example.shopique.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val isProductFavoriteUseCase: IsProductFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            getProductById(productId)
        }
    }

    private fun getProductById(id: Int) {
        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            getProductByIdUseCase(id).fold(

                onSuccess = { product ->
                    val isFavorite = isProductFavoriteUseCase(id)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            product = product,
                            isFavorite = isFavorite,
                            error = ""
                        )
                    }
                },

                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Something went wrong"
                        )
                    }
                }
            )
        }
    }

    fun toggleFavorite() {
        val product = _state.value.product ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(
                FavoriteProduct(
                    id = product.id,
                    title = product.title,
                    price = product.price,
                    image = product.thumbnail,
                    category = product.category
                )
            )
            _state.update {
                it.copy(isFavorite = !it.isFavorite)
            }
        }
    }

    fun addToCart() {

        val product = _state.value.product ?: return

        viewModelScope.launch {

            addToCartUseCase(
                CartItem(
                    id = product.id,
                    title = product.title,
                    price = product.price,
                    image = product.thumbnail,
                    quantity = 1
                )
            )
        }
    }
}