package ee.topgravity.android.testassignment.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ee.topgravity.android.testassignment.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow<ProductListState>(ProductListState.Loading)

    val state = _state.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            runCatching {
                repository.getProducts(20)
            }.onSuccess { products ->
                _state.value = ProductListState.Success(products)
            }.onFailure { error ->
                _state.value = ProductListState.Error(
                    error.message ?: "Failed to load products"
                )
            }
        }
    }

    fun retryLoading() {
        _state.value = ProductListState.Loading
        loadProducts()
    }
}