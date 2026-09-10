package ee.topgravity.android.testassignment.ui.products
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ee.topgravity.android.testassignment.data.model.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = koinViewModel()
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
            ProductList(currentState.products)
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductItem(product)
        }
    }
}

@Composable
fun ProductItem(item: Product) {
    ListItem(
        headlineContent = { Text(text = item.title) },
        trailingContent = { AsyncImage(
            model = item.thumbnail,
            contentDescription = item.description,
        ) }
    )
}

@Composable
private fun LoadingContent() {
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
        Text(text = message)
    }
}