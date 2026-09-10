package ee.topgravity.android.testassignment.ui.products

import ee.topgravity.android.testassignment.data.model.Product

sealed interface ProductListState {

    data object Loading : ProductListState

    data class Success(
        val products: List<Product>
    ) : ProductListState

    data class Error(
        val message: String
    ) : ProductListState
}