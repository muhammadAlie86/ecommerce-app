package com.example.ecommerce.view.screen.home

import androidx.compose.runtime.Composable
import com.example.ecommerce.domain.model.Product

@Composable
fun HomeContent(
    username: String,
    onNavigateToCart: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    categoryItem : List<Product>,
    onClick: (String) -> Unit,
    onProductClick: (Int) -> Unit,
    showBottomSheet: Boolean = false,
    onDismissBottomSheet: () -> Unit = {},
    bottomSheetContent: @Composable () -> Unit = {},
    cartItemCount : Int = 0,
    isLoading : Boolean = false
) {
    HomeView(
        username,
        onNavigateToCart,
        onNavigateToProfile,
        categoryItem,
        onClick,
        onProductClick,
        showBottomSheet,
        onDismissBottomSheet,
        bottomSheetContent,
        cartItemCount,
        isLoading
    )
}

