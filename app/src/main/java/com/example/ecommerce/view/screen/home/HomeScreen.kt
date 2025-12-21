package com.example.ecommerce.view.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce.R
import com.example.ecommerce.view.component.DialogConfirmation

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToCategoryDetail : (String) -> Unit,
    onNavigateToProductDetail : (Int) -> Unit,
    onNavigateToCart : () -> Unit,
    onNavigateToLogin : () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiLoadingState by viewModel.uiLoadingState.collectAsStateWithLifecycle()
    DialogConfirmation(
        visible = uiState.isDialogVisible,
        onYesAction = {
            viewModel.showDialog(false)
            viewModel.removeUser()
            onNavigateToLogin()
        },
        onNoAction = {
            viewModel.showDialog(false)
        },
        title = stringResource(id = R.string.logout_confirmation)

    )
    HomeContent(
        username = uiState.username,
        onNavigateToCart = {
            if (uiState.cartItemCount > 0) {
                onNavigateToCart()
            } else {
                viewModel.handleError(Throwable("Keranjang kosong!"))
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
                SheetContent(user, logout = {
                    viewModel.showDialog(true)
                })
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