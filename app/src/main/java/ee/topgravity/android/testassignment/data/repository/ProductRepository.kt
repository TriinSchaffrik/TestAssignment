package ee.topgravity.android.testassignment.data.repository

import ee.topgravity.android.testassignment.data.model.Product
import ee.topgravity.android.testassignment.data.service.ProductApi

interface ProductRepository {
    suspend fun getProducts(limit: Int): List<Product>
}

class ProductRepositoryImpl(
    private val service: ProductApi
) : ProductRepository {

    override suspend fun getProducts(limit: Int): List<Product> {
        return service.getProducts(limit = limit).products
    }
}