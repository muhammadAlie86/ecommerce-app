package com.example.ecommerce.view.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecommerce.R
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.model.User
import com.example.ecommerce.view.component.responsiveTextSize
import com.example.ecommerce.view.component.shimmerEffect
import com.example.ecommerce.view.theme.PoppinsBold
import com.example.ecommerce.view.theme.PoppinsMedium
import com.example.ecommerce.view.theme.White
import com.example.ecommerce.view.theme.darker_grey
import com.example.ecommerce.view.theme.gold
import com.example.ecommerce.view.theme.grey_500
import com.example.ecommerce.view.theme.grey_600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
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
    val sheetState = rememberModalBottomSheetState()
    Scaffold(
        modifier =
            Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(modifier = Modifier.weight(0.6f)) {
                            HomeHeaderLeftView(username)
                        }
                        Box(modifier = Modifier.weight(0.4f), contentAlignment = Alignment.CenterEnd) {
                            HomeHeaderRightView(onNavigateToCart, onNavigateToProfile, cartItemCount)
                        }
                    }
                }

            )
        },
        content = {innerPadding ->
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    if (isLoading) {
                        CategoryShimmer()
                    } else {
                        CardCategoryItemView(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { category ->
                                onClick(category)
                            },
                            categoryItem = categoryItem
                        )
                    }
                }

                item(span = StaggeredGridItemSpan.FullLine ) {
                    Text(
                        text = "Produk",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (isLoading) {
                    items(6) {
                        ProductItemShimmer()
                    }
                } else {


                    items(categoryItem) { product ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            colors =
                                CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                ),
                        ) {
                            ProductItemView(
                                image = product.imageUrl,
                                title = product.title,
                                price = product.price,
                                rating = product.ratingDetails.rate,
                                count = product.ratingDetails.count,
                                onProductClick = {
                                    onProductClick(product.id)
                                },
                            )
                        }
                    }
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = onDismissBottomSheet,
                    sheetState = sheetState,
                    containerColor = MaterialTheme.colorScheme.surface,
                    dragHandle = { BottomSheetDefaults.DragHandle() }
                ) {
                    bottomSheetContent()
                }
            }

        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeaderRightView(
    onNavigateToCart: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    cartItemCount: Int = 0
) {
    Row(
        modifier = Modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BadgedBox(
            modifier = Modifier.clickable { onNavigateToCart() },
            badge = {
                if (cartItemCount > 0) {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = White
                    ) {
                        Text(text = cartItemCount.toString())
                    }
                }
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shopping_chart),
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentHeight()
                    .clickable{
                        onNavigateToCart()
                    },
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .wrapContentHeight()
                .clickable{
                    onNavigateToProfile()
                },
        )
    }
}
@Composable
fun HomeHeaderLeftView(
    username : String
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier.padding(end = 3.dp),
            text = stringResource(R.string.greeting),
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3.5f)
                .copy(
                    color = grey_600,
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsMedium
                ),
        )
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = username,
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3.5f)
                .copy(
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsBold
                ),
        )
    }
}

@Composable
fun CardCategoryItemView(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    categoryItem : List<Product>
) {
    val categories = remember(categoryItem) {
        categoryItem.distinctBy { it.category }
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                text =  stringResource(R.string.category),
                style =
                    TextStyle.Default
                        .responsiveTextSize(baseFontSize = 3.5f)
                        .copy(
                            color = darker_grey,
                            textAlign = TextAlign.Center,
                        ),
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(items = categories) { _, product ->
                    CategoryItemView(
                        image = product.imageUrl,
                        label = product.category,
                        onClick = {
                            onClick(product.category)
                        }
                    )
                }
            }
        }


    }

}
@Composable
fun CategoryItemView(
    modifier: Modifier = Modifier,
    image: String,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .clickable { onClick() }
                .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = modifier.wrapContentSize(),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = "image url category",
                modifier = Modifier
                    .size(60.dp)
                    .padding(8.dp),
                contentScale = ContentScale.FillHeight,
                error = painterResource(R.drawable.ic_error_image)
            )
        }
        Text(
            text =  label,
            style =
                TextStyle.Default
                    .responsiveTextSize(baseFontSize = 3f)
                    .copy(
                        color = darker_grey,
                        textAlign = TextAlign.Center,
                    ),
        )
    }
}


@Composable
fun ProductItemView(
    onProductClick: () -> Unit,
    image : String,
    title : String,
    price : Double,
    rating : Double,
    count : Int
){
    Column(
        modifier =
            Modifier
                .clickable { onProductClick() }
                .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "image url product",
            modifier = Modifier
                .wrapContentSize()
                .padding(4.dp),
            contentScale = ContentScale.FillHeight,
            error = painterResource(R.drawable.ic_error_image)
        )
        Text(
            modifier = Modifier.padding(end = 3.dp),
            text = title,
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3f)
                .copy(
                    color = grey_600,
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsMedium
                ),
        )
        RatingBarView(
            rating = rating,
        )
        ProductPriceCountView(
            price = price,
            count = count
        )

    }
}
@Composable
fun RatingBarView(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starSize: Dp = 16.dp,
    starsColor: Color = gold
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(stars) { index ->
            val starIndex = index + 1
            val icon = when {
                rating >= starIndex -> Icons.Rounded.Star
                rating >= starIndex - 0.5 -> Icons.Rounded.StarHalf
                else -> Icons.Rounded.StarOutline
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier.size(starSize)
            )
        }
        Text(
            text = "$rating",
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 2.5f)
                .copy(
                    color = grey_600,
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsMedium
                ),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
@Composable
fun ProductPriceCountView(
    price : Double,
    count : Int
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier.padding(end = 3.dp),
            text = "$$price",
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3.5f)
                .copy(
                    color = grey_600,
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsMedium
                ),
        )
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = "$count terjual",
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 2f)
                .copy(
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsBold
                ),
        )
    }
}
@Composable
fun SheetContent(
    user: User
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SheetContentView(
            label = "Nama",
            value = user.fullName
        )

        SheetContentView(label = "Username", value = user.username)
        SheetContentView(label = "Email", value = user.email)
        SheetContentView(label = "Telepon", value = user.phoneNumber)

        val fullAddress = "${user.userAddress.street} No.${user.userAddress.streetNumber}, ${user.userAddress.city}"
        SheetContentView(
            label = "Alamat",
            value = fullAddress,
            showDivider = false
        )
    }
}
@Composable
fun SheetContentView(
    label: String,
    value: String,
    showDivider: Boolean = true // Tambahkan opsi untuk menyembunyikan garis terakhir
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (label.isNotEmpty()) {
                Text(
                    text = label,
                    style = TextStyle.Default
                        .responsiveTextSize(baseFontSize = 3.5f)
                        .copy(
                            color = grey_600,
                            fontFamily = PoppinsMedium
                        ),
                )
            }
            Text(
                text = value,
                modifier = Modifier.weight(1f, fill = false),
                style = TextStyle.Default
                    .responsiveTextSize(baseFontSize = 3.5f)
                    .copy(
                        textAlign = TextAlign.End,
                        fontFamily = PoppinsMedium
                    ),
            )
        }
        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(top = 4.dp),
                thickness = 1.dp,
                color = grey_500
            )
        }
    }
}
@Composable
fun ProductItemShimmer() {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // Placeholder Gambar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .shimmerEffect()
            )

            Column(modifier = Modifier.padding(8.dp)) {
                // Placeholder Judul (2 baris)
                Box(modifier = Modifier.fillMaxWidth().height(14.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.fillMaxWidth(0.7f).height(14.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())

                Spacer(modifier = Modifier.height(8.dp))

                // Placeholder Rating & Harga
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(60.dp, 12.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.size(20.dp, 12.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Placeholder Harga
                Box(modifier = Modifier.size(80.dp, 18.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
            }
        }
    }
}
@Composable
fun CategoryShimmer() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(4) {
            Box(modifier = Modifier.size(70.dp).clip(RoundedCornerShape(12.dp)).shimmerEffect())
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewHomeHeaderWithItems() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            HomeHeaderRightView(
                cartItemCount = 6, // Skenario keranjang berisi (seperti data JSON Anda)
                onNavigateToCart = {},
                onNavigateToProfile = {}
            )
        }
    }
}