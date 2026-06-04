package com.example.shopique

import com.example.shopique.domain.model.Product
import com.example.shopique.domain.repository.ProductRepository
import com.example.shopique.domain.usecase.GetProductByIdUseCase
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
class GetProductByIdUseCaseTest {

    private val repository: ProductRepository = mock()
    private lateinit var useCase: GetProductByIdUseCase
    private val testDispatcher = UnconfinedTestDispatcher()

    private val fakeProduct = Product(
        id = 1,
        title = "Backpack",
        price = 9.99,
        description = "Your perfect pack for everyday use",
        images = listOf(
            "https://image1.jpg",
            "https://image2.jpg",
            "https://image3.jpg"
        ),
        thumbnail = "https://image1.jpg",
        category = "Bags"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = GetProductByIdUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetches correct product when tapped from list`() = runTest {
        whenever(repository.getProductById(1))
            .thenReturn(Result.success(fakeProduct))

        val result = useCase(1)

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
        assertEquals(1, result.getOrNull()?.id)
    }

    @Test
    fun `product detail contains correct title`() = runTest {
        whenever(repository.getProductById(1))
            .thenReturn(Result.success(fakeProduct))

        val result = useCase(1)

        assertEquals("Backpack", result.getOrNull()?.title)
    }

    @Test
    fun `product detail contains correct price`() = runTest {
        whenever(repository.getProductById(1))
            .thenReturn(Result.success(fakeProduct))

        val result = useCase(1)

        // ✅ Fixed Double? unwrap
        val price = result.getOrNull()?.price
        assertNotNull(price)
        assertEquals(9.99, price!!, 0.0)
    }

    @Test
    fun `product detail contains all images for carousel`() = runTest {
        whenever(repository.getProductById(1))
            .thenReturn(Result.success(fakeProduct))

        val result = useCase(1)

        val images = result.getOrNull()?.images
        assertNotNull(images)
        assertTrue(images!!.isNotEmpty())
        assertEquals(3, images.size)
    }

    @Test
    fun `product has multiple images for horizontal carousel swipe`() = runTest {
        whenever(repository.getProductById(1))
            .thenReturn(Result.success(fakeProduct))

        val result = useCase(1)

        val images = result.getOrNull()?.images
        assertTrue(images!!.size > 1)
    }

    @Test
    fun `returns failure when product not found`() = runTest {
        whenever(repository.getProductById(99))
            .thenReturn(Result.failure(Exception("Product not found")))

        val result = useCase(99)

        assertTrue(result.isFailure)
        assertEquals("Product not found", result.exceptionOrNull()?.message)
    }
}