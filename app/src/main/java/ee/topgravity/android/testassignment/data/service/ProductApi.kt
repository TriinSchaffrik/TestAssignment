package ee.topgravity.android.testassignment.data.service

import ee.topgravity.android.testassignment.data.model.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int
    ): ProductResponseDto

}