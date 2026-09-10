package ee.topgravity.android.testassignment.data.model
import kotlinx.serialization.Serializable

@Serializable
data class Product (
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String
)