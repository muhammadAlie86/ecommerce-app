package com.example.ecommerce.view.screen.cart

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecommerce.R
import com.example.ecommerce.core.data.remote.models.common.CartItemUIModel
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.libraries.utils.ConstantsMessage.DEFAULT_MESSAGE
import com.example.ecommerce.view.component.ContainerBody
import com.example.ecommerce.view.component.DialogCommon
import com.example.ecommerce.view.component.FullScreenLoading
import com.example.ecommerce.view.component.TopAppBarWithNavIcon
import com.example.ecommerce.view.component.responsiveTextSize
import com.example.ecommerce.view.screen.home.CardCategoryItemView
import com.example.ecommerce.view.screen.home.CategoryShimmer
import com.example.ecommerce.view.screen.home.HomeHeaderLeftView
import com.example.ecommerce.view.screen.home.HomeHeaderRightView
import com.example.ecommerce.view.screen.home.ProductItemShimmer
import com.example.ecommerce.view.screen.home.ProductItemView
import com.example.ecommerce.view.theme.Black
import com.example.ecommerce.view.theme.PoppinsMedium
import com.example.ecommerce.view.theme.PoppinsRegular
import com.example.ecommerce.view.theme.PoppinsSemiBold
import com.example.ecommerce.view.theme.White
import com.example.ecommerce.view.theme.darker_grey
import com.example.ecommerce.view.theme.grey_500
import com.example.ecommerce.view.theme.orange
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    popBackStack: () -> Unit,
    uiItems: List<CartItemUIModel>,
    totalPrice: Double,
    selectedCount: Int,
    isAllSelected: Boolean,
    onToggleAll: (Boolean) -> Unit,
    onProductChecked: (Int, Boolean) -> Unit,
    onQuantityChanged: (Int, Int) -> Unit,
    onRemove: (Int) -> Unit,
    onCheckout: () -> Unit,
    onUpdate: (CartItemUIModel) -> Unit,
    similarProducts: List<Product>,
    onSimilarProductSelected: (oldProductId: Int, newProduct: Product) -> Unit,
    onSwipeClick: (Int) -> Unit = {},
    isLoading : Boolean = false,
    isError : Boolean = false,
    onAction: () -> Unit = {},
    onDismissError: () -> Unit = {},
    errorMessage: Throwable? = null
) {
    val swipedItemIds = remember { mutableStateListOf<Int>() }
    var showSheet by remember { mutableStateOf(false) }
    var selectedItemToReplace by remember { mutableStateOf<CartItemUIModel?>(null) }
    val sheetState = rememberModalBottomSheetState()
    ContainerBody(
        topBar = {
            TopAppBarWithNavIcon(
                titleResId = "Keranjang Saya",
                pressOnBack = popBackStack
            )
        },
        bottomBar = {
            CartBottomBar(
                totalPrice = totalPrice,
                totalItems = selectedCount,
                isAllSelected = isAllSelected,
                onSelectAllChange = onToggleAll,
                onCheckout = onCheckout
            )
        }
    ) { padding ->


        DialogCommon(
            label = R.string.information,
            visible = isError,
            onDismiss = onDismissError,
            action = onAction,
            message = errorMessage?.localizedMessage ?: DEFAULT_MESSAGE
        )


        FullScreenLoading(visible = isLoading)
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                SimilarProductsSheetContent(
                    category = selectedItemToReplace?.category ?: "",
                    products = similarProducts,
                    onProductClick = { newProduct ->
                        selectedItemToReplace?.let { oldItem ->
                            onSimilarProductSelected(oldItem.productId, newProduct)
                        }
                        showSheet = false
                    }
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column {

                        uiItems.forEachIndexed { index, item ->
                            SwipeableProductRow(
                                item = item,
                                onCheckedChange = { onProductChecked(item.productId, it) },
                                onQuantityChange = { id, newQty ->
                                    onQuantityChanged(id, newQty)
                                },
                                onRemove = onRemove,
                                isSwiped = swipedItemIds.isNotEmpty(),

                                onUpdate = {
                                    selectedItemToReplace = item
                                    showSheet = true
                                    onUpdate(item)
                                },
                                onSwipeStateChanged = {isOpen ->
                                    if (isOpen) {
                                        if (!swipedItemIds.contains(item.productId)) {
                                            swipedItemIds.add(item.productId)
                                        }
                                    } else {
                                        swipedItemIds.remove(item.productId)
                                    }
                                },
                                onSwipeClick = onSwipeClick

                            )

                            if (index < uiItems.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = grey_500
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ProductRow(
    item: CartItemUIModel,
    onCheckedChange: (Boolean) -> Unit,
    onQuantityChange: (Int, Int) -> Unit,
    isSwiped: Boolean = false,
    onSwipeClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .clickable { onSwipeClick(item.productId) },
            text = if (isSwiped) "selesai" else "ubah",
            style = TextStyle.Default.responsiveTextSize(
                baseFontSize = 3.5f
            ).copy(
                color = darker_grey,
                textAlign = TextAlign.End,
                fontFamily = PoppinsMedium,
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = item.isSelected,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(checkedColor = Red)
            )

            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(85.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background),
                contentScale = ContentScale.FillHeight
            )

            Column(
                modifier = Modifier.padding(start = 12.dp).weight(1f)
            ) {
                Text(
                    text = item.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp,
                    style = TextStyle.Default.responsiveTextSize(
                        baseFontSize = 3.5f
                    ).copy(
                        color = darker_grey,
                        textAlign = TextAlign.Start,
                        fontFamily = PoppinsMedium,
                    )

                )
                Text(
                    text = item.category,
                    style = TextStyle.Default.responsiveTextSize(
                        baseFontSize = 2.5f
                    ).copy(
                        color = darker_grey,
                        textAlign = TextAlign.Start,
                        fontFamily = PoppinsMedium,
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$ ${item.price}",
                        color = orange,
                        style = TextStyle.Default.responsiveTextSize(
                            baseFontSize = 4f
                        ).copy(
                            color = orange,
                            textAlign = TextAlign.Start,
                            fontFamily = PoppinsSemiBold
                        )

                    )

                    QuantityStepper(
                        quantity = item.quantity,
                        onIncrease =  { onQuantityChange(item.productId, item.quantity + 1) },
                        onDecrease = {
                            if (item.quantity > 1) {
                                onQuantityChange(item.productId, item.quantity - 1)
                            }
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun QuantityStepper(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(1.dp, grey_500, RoundedCornerShape(4.dp))
            .height(28.dp)
    ) {
        IconButton(onClick = onDecrease, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Remove, null, modifier = Modifier.size(16.dp),
                tint = if (quantity > 1) Color.Black else grey_500)
        }

        VerticalDivider(color = grey_500, modifier = Modifier.fillMaxHeight().width(1.dp))

        Text(
            text = quantity.toString(),
            modifier = Modifier.widthIn(min = 35.dp),
            textAlign = TextAlign.Center,
            style = TextStyle.Default.responsiveTextSize(
                baseFontSize = 3f
            ).copy(
                color = darker_grey,
                textAlign = TextAlign.Center,
                fontFamily = PoppinsRegular,
            )

        )

        VerticalDivider(color = grey_500, modifier = Modifier.fillMaxHeight().width(1.dp))

        IconButton(onClick = onIncrease, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun CartBottomBar(
    totalPrice: Double,
    totalItems: Int,
    isAllSelected: Boolean,
    onSelectAllChange: (Boolean) -> Unit,
    onCheckout: () -> Unit
) {
    Surface(shadowElevation = 15.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAllSelected,
                onCheckedChange = onSelectAllChange,
                colors = CheckboxDefaults.colors(checkedColor = orange)
            )
            Text("Semua",
                style = TextStyle.Default.responsiveTextSize(
                baseFontSize = 3.5f
            ).copy(
                color = darker_grey,
                textAlign = TextAlign.Start,
                fontFamily = PoppinsMedium,
            ))

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Total $${String.format(java.util.Locale.US, "%,.2f", totalPrice)}",
                    style = TextStyle.Default.responsiveTextSize(
                        baseFontSize = 4f
                    ).copy(
                        color = orange,
                        textAlign = TextAlign.Start,
                        fontFamily = PoppinsSemiBold,
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    onCheckout()
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                enabled = totalItems > 0,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text("Checkout ($totalItems)",
                    style = TextStyle.Default.responsiveTextSize(
                    baseFontSize = 3.5f
                ).copy(
                    color = Black,
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsMedium,
                ))
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableProductRow(
    item: CartItemUIModel,
    onCheckedChange: (Boolean) -> Unit,
    onQuantityChange: (Int, Int) -> Unit,
    onRemove: (Int) -> Unit,
    onUpdate: (CartItemUIModel) -> Unit,
    onSwipeStateChanged: (Boolean) -> Unit,
    isSwiped: Boolean,
    onSwipeClick: (Int) -> Unit = {}
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    val decaySpec = rememberSplineBasedDecay<Float>()

    val actionButtonsWidth = with(density) { 160.dp.toPx() }

    val anchors = remember(actionButtonsWidth) {
        DraggableAnchors {
            DragValue.Settled at 0f
            DragValue.Opened at -actionButtonsWidth
        }
    }

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Settled,
            anchors = anchors,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = tween(durationMillis = 250),
            decayAnimationSpec = decaySpec
        )
    }

    LaunchedEffect(state.currentValue) {
        onSwipeStateChanged(state.currentValue == DragValue.Opened)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(White)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .background(orange)
                    .clickable {
                        onUpdate(item)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Serupa",
                    style = TextStyle.Default.responsiveTextSize(
                        baseFontSize = 3f
                    ).copy(
                        color = White,
                        textAlign = TextAlign.Start,
                        fontFamily = PoppinsMedium,
                    )
                )
            }
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .background(Red)
                    .clickable {
                        onRemove(item.productId)
                        scope.launch { state.animateTo(DragValue.Settled) }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Hapus",
                    style = TextStyle.Default.responsiveTextSize(
                    baseFontSize = 3f
                ).copy(
                    color = White,
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsMedium,
                ))
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        x = state.requireOffset().roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(state, Orientation.Horizontal),
            color = White
        ) {
            ProductRow(
                item = item,
                onCheckedChange = onCheckedChange,
                onSwipeClick = onSwipeClick,
                onQuantityChange = { productId, newQty ->
                    onQuantityChange(productId, newQty)
                },
                isSwiped = isSwiped
            )
        }
    }
}
@Composable
fun SimilarProductsSheetContent(
    category: String,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(max = 400.dp)
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            item(span = StaggeredGridItemSpan.FullLine ) {
                Text(
                    text = "Pilih Produk Serupa ($category)",
                    style = TextStyle.Default.responsiveTextSize(
                        baseFontSize = 4f
                    ).copy(
                        color = Black,
                        textAlign = TextAlign.Start,
                        fontFamily = PoppinsMedium,
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (isLoading) {
                items(6) {
                    ProductItemShimmer()
                }
            } else {


                items(products) { product ->

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
                                onProductClick(product)
                            },
                        )
                    }
                }
            }
        }
    }
}
enum class DragValue { Settled, Opened }