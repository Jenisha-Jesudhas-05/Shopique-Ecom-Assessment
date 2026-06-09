package com.example.shopique.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopique.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state.asStateFlow()

    init {
        getProducts()
    }

    fun onSearchQueryChange(query: String) {

        _state.update {
            it.copy(searchQuery = query)
        }

        searchJob?.cancel()

        searchJob = viewModelScope.launch {

            delay(300)

            filterProducts()
        }
    }

    fun onCategorySelected(category: String) {
        _state.update {
            it.copy(selectedCategory = category)
        }
        filterProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            getProductsUseCase().fold(

                onSuccess = { products ->

                    _state.update {
                        it.copy(
                            isLoading = false,
                            products = products,
                            filteredProducts = products,
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

    private fun filterProducts() {

        val currentState = _state.value

        val filtered = currentState.products.filter { product ->

            val matchesSearch =
                product.title.contains(
                    currentState.searchQuery,
                    ignoreCase = true
                )

            val matchesCategory =
                currentState.selectedCategory == "All" ||
                        product.category.equals(
                            currentState.selectedCategory,
                            ignoreCase = true
                        )

            matchesSearch && matchesCategory
        }

        _state.update {
            it.copy(filteredProducts = filtered)
        }
    }


    private var searchJob: Job? = null
}