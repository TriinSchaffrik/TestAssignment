package ee.topgravity.android.testassignment.ui.products
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ee.topgravity.android.testassignment.data.model.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = koinViewModel(),
    onProductClick: (Product) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        ProductListState.Loading -> {
            LoadingContent()
        }

        is ProductListState.Error -> {
            ErrorContent(currentState.message)
        }

        is ProductListState.Success -> {
            ProductList(currentState.products, onProductClick)
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductClicked: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(products, key = { it.id }) { product ->
            ProductItem(product) {
                onProductClicked(product)
            }
        }
    }
}

@Composable
fun ProductItem(item: Product, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(item.title) },
        trailingContent = { 
            AsyncImage(
                model = item.thumbnail,
                contentDescription = item.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) 
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}