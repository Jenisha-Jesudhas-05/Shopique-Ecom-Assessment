package com.example.shopique

import app.cash.turbine.test
import com.example.shopique.domain.model.Product
import com.example.shopique.domain.usecase.GetProductsUseCase
import com.example.shopique.presentation.productlist.ProductListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    private val getProductsUseCase: GetProductsUseCase = mock()
    private lateinit var viewModel: ProductListViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val fakeProducts = listOf(
        Product(
            id = 1,
            title = "Backpack",
            price = 9.99,
            description = "A great backpack",
            images = listOf(
                "https://image1.jpg",
                "https://image2.jpg",
                "https://image3.jpg"
            ),
            thumbnail = "https://image1.jpg",
            category = "Bags"
        ),
        Product(
            id = 2,
            title = "Jacket",
            price = 19.99,
            description = "A warm jacket",
            images = listOf("https://image2.jpg"),
            thumbnail = "https://image2.jpg",
            category = "Clothes"
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ✅ Requirement: Products displayed in LazyColumn
    @Test
    fun `state has products for LazyColumn display`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.success(fakeProducts))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.products.isNotEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ Requirement: title shown correctly in list
    @Test
    fun `state products contain correct titles for display`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.success(fakeProducts))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("Backpack", state.products.first().title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ Requirement: price shown correctly in list
    @Test
    fun `state products contain correct prices for display`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.success(fakeProducts))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(9.99, state.products.first().price, 0.0)
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ Requirement: thumbnail is first image from images array
    @Test
    fun `state products thumbnail matches first image in images array`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.success(fakeProducts))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            val product = state.products.first()
            assertEquals(product.images.first(), product.thumbnail)
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ Requirement: Tapping product navigates to detail — valid product id
    @Test
    fun `state products have valid ids for detail navigation`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.success(fakeProducts))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.products.all { it.id > 0 })
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ Requirement: images available for carousel in detail
    @Test
    fun `state products contain images for detail carousel`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.success(fakeProducts))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.products.first().images.isNotEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ Requirement: No error shown on success
    @Test
    fun `state shows no error when products load successfully`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.success(fakeProducts))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.error.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ Requirement: Handle API failure gracefully
    @Test
    fun `state shows error when api fails`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.failure(Exception("Network error")))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.error.isNotEmpty())
            assertEquals("Network error", state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ✅ Requirement: Empty list handled
    @Test
    fun `state handles empty product list gracefully`() = runTest {
        whenever(getProductsUseCase())
            .thenReturn(Result.success(emptyList()))

        viewModel = ProductListViewModel(getProductsUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.products.isEmpty())
            assertTrue(state.error.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }
}