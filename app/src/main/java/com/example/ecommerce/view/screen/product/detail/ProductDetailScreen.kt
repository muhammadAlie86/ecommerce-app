package com.example.ecommerce.view.screen.product.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.ecommerce.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    popBackStack: () -> Unit = {},
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val product = uiState.product

    var isAnimating by remember { mutableStateOf(false) }
    var cartIconPosition by remember { mutableStateOf(Offset.Zero) }
    var startPosition by remember { mutableStateOf(Offset.Zero) }
    var localCartCount by remember { mutableIntStateOf(0) }

    // Sinkronisasi data awal dari product count
    LaunchedEffect(product?.ratingDetails?.count) {
        product?.ratingDetails?.count?.let { localCartCount = it }
    }

    // Animasi Flying Icon
    val animatedOffset by animateOffsetAsState(
        targetValue = if (isAnimating) cartIconPosition else startPosition,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        finishedListener = {
            if (isAnimating) {
                isAnimating = false
                localCartCount++ // Trigger badge pop animation
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
                            // viewModel.addToCart(it.id) // Panggil action sesungguhnya di sini
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
                        cartCount = localCartCount,
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

        // Overlay Flying Icon
        if (isAnimating) {
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .offset { IntOffset(animatedOffset.x.toInt(), animatedOffset.y.toInt()) },
                color = Color(0xFF26A69A),
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
}

@Composable

fun ProductInfoSection(price: String, soldCount: String, rating: String, desc: String) {

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = price, color = Color(0xFFD32F2F), fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Text(text = soldCount, color = Color.Gray, fontSize = 14.sp)



        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {

            Text(text = rating, fontWeight = FontWeight.Medium)

            Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))

        }



        Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)



        Text(text = "Deskripsi Produk", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = desc, color = Color.DarkGray, lineHeight = 20.sp)

    }

}


@Composable
fun ProductImageHeader(
    imageUrl: String,
    cartCount: Int,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onTargetPositioned: (Offset) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_error_image)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderButton(icon = Icons.Default.ArrowBack, onClick = onBackClick)

            // Cart Icon with Enhanced Badge
            CartBadgeIcon(
                cartCount = cartCount,
                onClick = onCartClick,
                modifier = Modifier.onGloballyPositioned {
                    onTargetPositioned(it.positionInWindow())
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBadgeIcon(
    cartCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animasi Pop (Membal) saat cartCount berubah
    val badgeScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        // Key agar animasi reset & jalan lagi tiap count berubah
        finishedListener = {},
        label = "badge_scale"
    )

    Box(modifier = modifier.padding(4.dp)) {
        BadgedBox(
            badge = {
                if (cartCount > 0) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        modifier = Modifier.scale(badgeScale)
                    ) {
                        // Animasi Transisi Angka (Slide up)
                        AnimatedContent(
                            targetState = cartCount,
                            transitionSpec = {
                                slideInVertically { it } + fadeIn() togetherWith
                                        slideOutVertically { -it } + fadeOut()
                            }, label = "count_anim"
                        ) { count ->
                            Text(text = if (count > 99) "99+" else count.toString())
                        }
                    }
                }
            }
        ) {
            HeaderButton(icon = Icons.Default.ShoppingCart, onClick = onClick)
        }
    }
}

@Composable
fun HeaderButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Surface(
        color = Color.Black.copy(alpha = 0.3f),
        shape = CircleShape,
        modifier = Modifier.size(40.dp),
        onClick = onClick
    ) {
        Icon(icon, null, tint = Color.White, modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun BottomPurchaseBar(
    price: String,
    onAddToCartClick: (Offset) -> Unit
) {
    var buttonOffset by remember { mutableStateOf(Offset.Zero) }

    Surface(shadowElevation = 16.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding() // Penting agar tidak tertutup sistem navigasi
                .height(60.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxHeight()
                    .background(Color(0xFF26A69A))
                    .onGloballyPositioned { buttonOffset = it.positionInWindow() }
                    .clickable { onAddToCartClick(buttonOffset) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.AddShoppingCart, null, tint = Color.White, modifier = Modifier.size(26.dp))
            }

            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .background(Color(0xFFEE4D2D))
                    .clickable { /* Handle Buy */ },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Beli Sekarang", color = Color.White, fontSize = 14.sp)
                Text(price, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}