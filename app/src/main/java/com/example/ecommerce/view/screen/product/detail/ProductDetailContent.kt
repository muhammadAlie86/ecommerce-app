package com.example.ecommerce.view.screen.product.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecommerce.R
import com.example.ecommerce.view.component.responsiveTextSize
import com.example.ecommerce.view.component.shimmerEffect
import com.example.ecommerce.view.theme.Black
import com.example.ecommerce.view.theme.PoppinsMedium
import com.example.ecommerce.view.theme.PoppinsSemiBold
import com.example.ecommerce.view.theme.White
import com.example.ecommerce.view.theme.darker_grey
import com.example.ecommerce.view.theme.gold
import com.example.ecommerce.view.theme.orange

@Composable
fun ProductDetailShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).shimmerEffect())
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).shimmerEffect())
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .width(120.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(80.dp).height(16.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            Spacer(modifier = Modifier.width(12.dp))
            Box(modifier = Modifier.size(16.dp).clip(CircleShape).shimmerEffect())
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .width(150.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(12.dp))

        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Box(modifier = Modifier.weight(0.3f).fillMaxHeight().shimmerEffect())
            Box(modifier = Modifier.weight(0.7f).fillMaxHeight().shimmerEffect())
        }
    }
}
@Composable

fun ProductInfoSection(price: String, soldCount: String, rating: String, desc: String) {

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = price,
                style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 5.5f)
            .copy(
                color = Red,
                fontFamily = PoppinsMedium
            ),)

        Text(
            text = soldCount,
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3.5f)
                .copy(
                    color = darker_grey,
                    fontFamily = PoppinsMedium
                ),
        )



        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(vertical = 8.dp)) {

            Text(
                text = rating,
                style = TextStyle.Default
                    .responsiveTextSize(baseFontSize = 3f)
                    .copy(
                        color = darker_grey,
                        fontFamily = PoppinsMedium
                    ),)

            Icon(Icons.Default.Star, null, tint = gold, modifier = Modifier.size(16.dp))

        }



        Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)



        Text(
            text = "Deskripsi Produk",
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 4.5f)
                .copy(
                    fontFamily = PoppinsSemiBold
                ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = desc,
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 4f)
                .copy(
                    color = darker_grey,
                    fontFamily = PoppinsMedium
                ),
            lineHeight = 20.sp
        )

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
            contentScale = ContentScale.FillHeight,
            placeholder = painterResource(id = R.drawable.ic_error_image)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderButton(icon = Icons.Default.ArrowBack, onClick = onBackClick)

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
    val badgeScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        finishedListener = {},
        label = "badge_scale"
    )

    Box(modifier = modifier.padding(4.dp)) {
        BadgedBox(
            badge = {
                if (cartCount > 0) {
                    Badge(
                        containerColor = Red,
                        contentColor = White,
                        modifier = Modifier.scale(badgeScale)
                    ) {
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
        color = Black.copy(alpha = 0.3f),
        shape = CircleShape,
        modifier = Modifier.size(40.dp),
        onClick = onClick
    ) {
        Icon(icon, null, tint = White, modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun BottomPurchaseBar(
    price: String,
    onAddToCartClick: (Offset) -> Unit,
    onChekout: () -> Unit
) {
    var buttonOffset by remember { mutableStateOf(Offset.Zero) }

    Surface(shadowElevation = 16.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(60.dp)
                .background(White),
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
                Icon(Icons.Outlined.AddShoppingCart, null, tint = White, modifier = Modifier.size(26.dp))
            }

            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .background(orange)
                    .clickable { onChekout()},
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text ="Beli Sekarang",
                    style = TextStyle.Default
                        .responsiveTextSize(baseFontSize = 3.5f)
                        .copy(
                            color = White,
                            fontFamily = PoppinsSemiBold
                        )
                    )
                Text(
                    text = price,
                    style = TextStyle.Default
                        .responsiveTextSize(baseFontSize = 3.5f)
                        .copy(
                            color = White,
                            fontFamily = PoppinsSemiBold
                        )
                )
            }
        }
    }
}