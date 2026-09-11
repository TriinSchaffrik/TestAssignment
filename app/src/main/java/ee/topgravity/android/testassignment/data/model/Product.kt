package ee.topgravity.android.testassignment.data.model
data class Product (
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String,
    val images: List<String>
)