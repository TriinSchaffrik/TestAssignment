package ee.topgravity.android.testassignment

import ee.topgravity.android.testassignment.data.model.Product
import ee.topgravity.android.testassignment.data.repository.ProductRepository
import ee.topgravity.android.testassignment.ui.products.ProductListState
import ee.topgravity.android.testassignment.ui.products.ProductListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val products = listOf(
        Product(
            id = 1,
            title = "product1",
            description = "product1 description",
            thumbnail = "https://example.com/product1.jpg",
            images = listOf(
                "https://example.com/product1.jpg"
            )
        ),
        Product(
            id = 2,
            title = "product2",
            description = "product2 description",
            thumbnail = "https://example.com/product2.jpg",
            images = listOf(
                "https://example.com/product2.jpg"
            )
        )
    )

    @Test
    fun `loading state`() = runTest {
        val repository = FakeProductRepository(products = products)
        val viewModel = ProductListViewModel(repository)
        assertEquals(
            ProductListState.Loading,
            viewModel.state.value
        )
    }
    
    @Test
    fun `success state`() = runTest {
        val repository = FakeProductRepository(products = products)
        val viewModel = ProductListViewModel(repository)
        testScheduler.advanceUntilIdle()
        assertEquals(
            ProductListState.Success(products),
            viewModel.state.value
        )
    }

    @Test
    fun `error state`() = runTest {
        val repository = FakeProductRepository(
            error = IOException("Network error")
        )
        val viewModel = ProductListViewModel(repository)
        testScheduler.advanceUntilIdle()
        assertEquals(
            ProductListState.Error("Network error"),
            viewModel.state.value
        )
    }
}

class FakeProductRepository(
    private val products: List<Product> = emptyList(),
    private val error: Throwable? = null
) : ProductRepository {

    override suspend fun getProducts(limit: Int): List<Product> {
        error?.let { throw it }
        return products
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}