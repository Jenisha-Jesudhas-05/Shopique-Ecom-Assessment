package com.example.shopique

import com.example.shopique.domain.model.Product
import com.example.shopique.domain.repository.ProductRepository
import com.example.shopique.domain.usecase.GetProductsUseCase
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
class GetProductsUseCaseTest {

    private val repository: ProductRepository = mock()
    private lateinit var useCase: GetProductsUseCase
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
        useCase = GetProductsUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetches product list successfully from api`() = runTest {
        whenever(repository.getProducts())
            .thenReturn(Result.success(fakeProducts))

        val result = useCase()

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
    }

    @Test
    fun `product list is not empty for LazyColumn display`() = runTest {
        whenever(repository.getProducts())
            .thenReturn(Result.success(fakeProducts))

        val result = useCase()

        assertTrue(result.getOrNull()?.isNotEmpty() == true)
    }

    @Test
    fun `product contains correct title from api response`() = runTest {
        whenever(repository.getProducts())
            .thenReturn(Result.success(fakeProducts))

        val result = useCase()

        assertEquals("Backpack", result.getOrNull()?.first()?.title)
    }

    @Test
    fun `product contains correct price from api response`() = runTest {
        whenever(repository.getProducts())
            .thenReturn(Result.success(fakeProducts))

        val result = useCase()

        // ✅ Fixed Double? unwrap
        val price = result.getOrNull()?.first()?.price
        assertNotNull(price)
        assertEquals(9.99, price!!, 0.0)
    }

    @Test
    fun `thumbnail is first image from images array`() = runTest {
        whenever(repository.getProducts())
            .thenReturn(Result.success(fakeProducts))

        val result = useCase()

        val product = result.getOrNull()?.first()
        assertEquals(product?.images?.first(), product?.thumbnail)
    }

    @Test
    fun `returns failure when api call fails`() = runTest {
        whenever(repository.getProducts())
            .thenReturn(Result.failure(Exception("Network error")))

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}