package ee.topgravity.android.testassignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ee.topgravity.android.testassignment.ui.products.LoadingContent
import ee.topgravity.android.testassignment.ui.products.ProductDetailScreen
import ee.topgravity.android.testassignment.ui.products.ProductListScreen
import ee.topgravity.android.testassignment.ui.products.ProductListState
import ee.topgravity.android.testassignment.ui.products.ProductListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ProductListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "products"
    ) {
        composable("products") {
            ProductListScreen(
                viewModel = viewModel,
                onProductClick = { product ->
                    navController.navigate("product/${product.id}")
                }
            )
        }

        composable(
            route = "product/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            val product = (state as? ProductListState.Success)?.products?.find { it.id == productId }

            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            } else {
                LoadingContent()
            }
        }
    }
}