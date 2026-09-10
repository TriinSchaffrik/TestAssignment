package ee.topgravity.android.testassignment.data.model
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val products: List<Product>
)