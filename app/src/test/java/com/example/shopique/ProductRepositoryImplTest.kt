package com.example.shopique

import com.example.shopique.data.remote.ApiService
import com.example.shopique.data.remote.dto.CategoryDto
import com.example.shopique.data.remote.dto.ProductDto
import com.example.shopique.data.remote.repository.ProductRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryImplTest {

    private val apiService: ApiService = mock()
    private lateinit var repository: ProductRepositoryImpl
    private val testDispatcher = UnconfinedTestDispatcher()

    private val fakeProductDto = ProductDto(
        id = 1,
        title = "Backpack",
        price = 9.99,
        description = "Your perfect pack for everyday use",
        images = listOf(
            "https://image1.jpg",
            "https://image2.jpg",
            "https://image3.jpg"
        ),
        category = CategoryDto(
            id = 1,
            name = "Bags",
            image = "https://category.jpg"
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = ProductRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProducts fetches from api successfully`() = runTest {
        whenever(apiService.getProducts())
            .thenReturn(listOf(fakeProductDto))

        val result = repository.getProducts()

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
    }

    @Test
    fun `getProducts maps title correctly`() = runTest {
        whenever(apiService.getProducts())
            .thenReturn(listOf(fakeProductDto))

        val result = repository.getProducts()

        assertEquals("Backpack", result.getOrNull()?.first()?.title)
    }

    @Test
    fun `getProducts maps price correctly`() = runTest {
        whenever(apiService.getProducts())
            .thenReturn(listOf(fakeProductDto))

        val result = repository.getProducts()

        // ✅ Fixed
        val price = result.getOrNull()?.first()?.price
        assertNotNull(price)
        assertEquals(9.99, price!!, 0.0)
    }

    @Test
    fun `getProducts maps first image as thumbnail`() = runTest {
        whenever(apiService.getProducts())
            .thenReturn(listOf(fakeProductDto))

        val result = repository.getProducts()

        assertEquals(
            "https://image1.jpg",
            result.getOrNull()?.first()?.thumbnail
        )
    }

    @Test
    fun `getProducts maps all images for carousel`() = runTest {
        whenever(apiService.getProducts())
            .thenReturn(listOf(fakeProductDto))

        val result = repository.getProducts()

        assertEquals(3, result.getOrNull()?.first()?.images?.size)
    }

    @Test
    fun `getProducts returns failure on network error`() = runTest {
        whenever(apiService.getProducts())
            .thenThrow(RuntimeException("Network error"))

        val result = repository.getProducts()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getProductById returns correct product for detail page`() = runTest {
        whenever(apiService.getProductById(1))
            .thenReturn(fakeProductDto)

        val result = repository.getProductById(1)

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.id)
        assertEquals("Backpack", result.getOrNull()?.title)

        // ✅ Fixed
        val price = result.getOrNull()?.price
        assertNotNull(price)
        assertEquals(9.99, price!!, 0.0)
    }

    @Test
    fun `getProductById returns all images for carousel`() = runTest {
        whenever(apiService.getProductById(1))
            .thenReturn(fakeProductDto)

        val result = repository.getProductById(1)

        assertEquals(3, result.getOrNull()?.images?.size)
    }

    @Test
    fun `getProductById returns failure when api fails`() = runTest {
        whenever(apiService.getProductById(1))
            .thenThrow(RuntimeException("Product not found"))

        val result = repository.getProductById(1)

        assertTrue(result.isFailure)
        assertEquals(
            "Product not found",
            result.exceptionOrNull()?.message
        )
    }
}