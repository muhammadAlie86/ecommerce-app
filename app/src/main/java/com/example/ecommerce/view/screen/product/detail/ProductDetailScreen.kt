package com.example.ecommerce.view.screen.product.detail

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce.view.theme.cyan

@OptIn(ExperimentalMaterial3Api::class)@Composable
fun ProductDetailScreen(
    popBackStack: () -> Unit = {},
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val product = uiState.product

    var isAnimating by remember { mutableStateOf(false) }
    var cartIconPosition by remember { mutableStateOf(Offset.Zero) }
    var startPosition by remember { mutableStateOf(Offset.Zero) }

    val animatedOffset by animateOffsetAsState(
        targetValue = if (isAnimating) cartIconPosition else startPosition,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        finishedListener = {
            if (isAnimating) {
                isAnimating = false
            }
        }, label = "flying_cart"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            bottomBar = {
                product?.let {
                    BottomPurchaseBar(
                        price = "$ ${it.price}",
                        onAddToCartClick = { offset ->
                            startPosition = offset
                            isAnimating = true
                            viewModel.addProductToCart(it)
                        },
                        onChekout = {
                            viewModel.checkout()
                        }
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                product?.let { item ->
                    ProductImageHeader(
                        imageUrl = item.imageUrl,
                        cartCount = uiState.cartItemCount,
                        onBackClick = popBackStack,
                        onCartClick = { /* Navigate to Cart */ },
                        onTargetPositioned = { cartIconPosition = it }
                    )

                    ProductInfoSection(
                        price = "$ ${item.price}",
                        soldCount = "${item.ratingDetails.count} Terjual",
                        rating = item.ratingDetails.rate.toString(),
                        desc = item.description
                    )
                }
            }
        }

        if (isAnimating) {
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .offset { IntOffset(animatedOffset.x.toInt(), animatedOffset.y.toInt()) },
                color = cyan,
                shape = CircleShape,
                shadowElevation = 8.dp
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
    LaunchedEffect(uiState.userId) {
        if (uiState.userId != -1) {
            viewModel.getCartUser(uiState.userId)
        }
    }
}