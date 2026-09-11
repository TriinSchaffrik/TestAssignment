package ee.topgravity.android.testassignment.ui.products
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ee.topgravity.android.testassignment.R
import ee.topgravity.android.testassignment.data.model.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = koinViewModel(),
    onProductClick: (Product) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val currentState = state) {
                ProductListState.Loading -> {
                    LoadingContent()
                }

                is ProductListState.Error -> {
                    ErrorContent(currentState.message) { viewModel.retryLoading() }
                }

                is ProductListState.Success -> {
                    if (currentState.products.isEmpty()) {
                        ErrorContent(stringResource(id = R.string.error_no_products)) { viewModel.retryLoading() }
                    } else {
                        ProductList(currentState.products, onProductClick)
                    }
                }
            }
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
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 30.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
fun ProductItem(item: Product, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(item.title) },
        leadingContent = {
            AsyncImage(
                model = item.thumbnail,
                contentDescription = item.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) 
        },
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 5.dp),
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
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = message)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRetry) {
                Text(stringResource(id = R.string.btn_retry))
            }
        }
    }
}