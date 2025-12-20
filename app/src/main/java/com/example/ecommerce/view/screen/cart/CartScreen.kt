package com.example.ecommerce.view.screen.cart

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce.R
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.domain.model.CartItem
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.model.Rating
import com.example.ecommerce.view.component.DialogConfirmation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiStateError by viewModel.uiStateError.collectAsStateWithLifecycle()
    DialogConfirmation(
        visible = uiState.isDialogVisible,
        onYesAction = {
            uiState.selectedProductId?.let { id ->
                viewModel.deleteProductFromCart(uiState.userId, id)
            }
        },
        onNoAction = {
            viewModel.onDismissDialog()
        },
        title = stringResource(id = R.string.cancelled)

    )
    CartContent(
        uiItems = uiState.uiItems,
        totalPrice = uiState.totalPrice,
        selectedCount = uiState.selectedCount,
        isAllSelected = uiState.isAllSelected,
        onToggleAll = { viewModel.onToggleAll(it) },
        onProductChecked = { id, checked -> viewModel.onProductCheckedChange(id, checked) },
        onQuantityChanged = { id, qty -> viewModel.updateQuantity(id, qty) },
        similarProducts = uiState.similarProducts,
        onRemove = {productId ->
            viewModel.onShowDeleteDialog(productId)
        },
        onCheckout = {
            viewModel.checkout()
        },
        onUpdate = {item ->
            viewModel.onSameProductActionTriggered(item)
        },
        onSimilarProductSelected = { oldId, newProduct ->
            viewModel.replaceProductInCart(oldId, newProduct)
        },
    )
    LaunchedEffect(uiState.userId) {
        if (uiState.userId != -1) {
            viewModel.loadInitialData(uiState.userId)
        }
    }
}
