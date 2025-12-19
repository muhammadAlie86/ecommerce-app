package com.example.ecommerce.view.screen.product.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce.view.component.ContainerBody
import com.example.ecommerce.view.component.TopAppBarWithNavIcon

@Composable
fun CategoryDetailScreen(
    viewModel: CategoryDetailViewModel = hiltViewModel(),
    onNavigateToProductDetail: (Int) -> Unit = {},
    onPopBackStack : () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiErrorState by viewModel.uiStateError.collectAsStateWithLifecycle()
    val uiLoadingState by viewModel.uiLoadingState.collectAsStateWithLifecycle()
    ContainerBody(
        topBar = {
            TopAppBarWithNavIcon(
                titleResId = "Keranjang Saya",
                pressOnBack = {
                    onPopBackStack()
                }
            )
        },
    ) {
        CardProductDetailContent(
            productItem = uiState.productList,
            onProductClick = {productId ->
                onNavigateToProductDetail(productId)

            }
        )
    }
}