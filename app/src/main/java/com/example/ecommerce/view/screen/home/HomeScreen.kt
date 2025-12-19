package com.example.ecommerce.view.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToCategoryDetail : (String) -> Unit,
    onNavigateToProductDetail : (Int) -> Unit,
    onNavigateToCart : () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiErrorState by viewModel.uiStateError.collectAsStateWithLifecycle()
    val uiLoadingState by viewModel.uiLoadingState.collectAsStateWithLifecycle()

    HomeContent(
        username = uiState.username,
        onNavigateToCart = {
            if (uiState.cartItemCount > 0) {
                onNavigateToCart()
            } else {
                println("Keranjang kosong!")
            }
        },
        onNavigateToProfile = {
            viewModel.getUser(uiState.userId)
        },
        categoryItem = uiState.productList,
        onClick = {category ->
            onNavigateToCategoryDetail(category)
        },
        onProductClick = {productId ->
            onNavigateToProductDetail(productId)
        },

        showBottomSheet = uiState.isSheetOpen,
        onDismissBottomSheet = {
            viewModel.isSheetOpen(false)
        },
        bottomSheetContent = {
            val user = uiState.user
            if (user != null){
                SheetContent(user)
            }
        },
        cartItemCount = uiState.cartItemCount,
        isLoading = uiLoadingState.isLoading
    )
    LaunchedEffect(uiState.user) {
        if (uiState.user != null) {
            viewModel.isSheetOpen(true)
        }
    }

}