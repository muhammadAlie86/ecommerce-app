package com.example.ecommerce.view.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Storefront
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecommerce.core.data.remote.models.common.CartItemUIModel
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    cartResponse: Cart,
    allProducts: List<Product>
) {
    // 1. State Hoisting dengan mutableStateListOf
    val uiItems = remember {
        mutableStateListOf<CartItemUIModel>().apply {
            addAll(cartResponse.products.mapNotNull { cartItem ->
                allProducts.find { it.id == cartItem.id }?.let { detail ->
                    CartItemUIModel(
                        productId = detail.id,
                        title = detail.title,
                        price = detail.price,
                        imageUrl = detail.imageUrl,
                        quantity = cartItem.quantity,
                        category = detail.category,
                        isSelected = false
                    )
                }
            })
        }
    }

    val isAllSelected by remember { derivedStateOf { uiItems.isNotEmpty() && uiItems.all { it.isSelected } } }
    val totalPrice by remember { derivedStateOf { uiItems.filter { it.isSelected }.sumOf { it.price * it.quantity } } }
    val selectedCount by remember { derivedStateOf { uiItems.count { it.isSelected } } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Keranjang (#${cartResponse.id})", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            CartBottomBar(
                totalPrice = totalPrice,
                totalItems = selectedCount,
                isAllSelected = isAllSelected,
                onSelectAllChange = { checked ->
                    uiItems.forEachIndexed { index, item ->
                        uiItems[index] = item.copy(isSelected = checked)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column {
                        CartStoreHeader(
                            userId = cartResponse.userId,
                            isAllSelected = isAllSelected,
                            onToggleAll = { checked ->
                                uiItems.forEachIndexed { index, item ->
                                    uiItems[index] = item.copy(isSelected = checked)
                                }
                            }
                        )

                        uiItems.forEachIndexed { index, item ->
                            ProductRow(
                                item = item,
                                onCheckedChange = { checked ->
                                    uiItems[index] = item.copy(isSelected = checked)
                                },
                                onQuantityChange = { newQty ->
                                    uiItems[index] = item.copy(quantity = newQty)
                                }
                            )
                            if (index < uiItems.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFF5F5F5)
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
fun CartStoreHeader(
    userId: Int,
    isAllSelected: Boolean,
    onToggleAll: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isAllSelected,
            onCheckedChange = onToggleAll,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFFF5722),
                uncheckedColor = Color.Gray
            )
        )

        Icon(
            imageVector = Icons.Default.Storefront,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.DarkGray
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "User ID: $userId Shop",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            ),
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Buka Toko",
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
    }
}
@Composable
fun ProductRow(
    item: CartItemUIModel,
    onCheckedChange: (Boolean) -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = item.isSelected,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF5722))
        )

        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(85.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF5F5F5)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(start = 12.dp).weight(1f)
        ) {
            Text(
                text = item.title,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
            Text(text = item.category, fontSize = 11.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rp${String.format("%,.0f", item.price * 15000).replace(',', '.')}",
                    color = Color(0xFFFF5722),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                QuantityStepper(
                    quantity = item.quantity,
                    onIncrease = { onQuantityChange(item.quantity + 1) },
                    onDecrease = { if (item.quantity > 1) onQuantityChange(item.quantity - 1) }
                )
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
            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
            .height(28.dp)
    ) {
        IconButton(onClick = onDecrease, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Remove, null, modifier = Modifier.size(16.dp),
                tint = if (quantity > 1) Color.Black else Color.LightGray)
        }

        VerticalDivider(color = Color.LightGray, modifier = Modifier.fillMaxHeight().width(1.dp))

        Text(
            text = quantity.toString(),
            modifier = Modifier.widthIn(min = 35.dp),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        VerticalDivider(color = Color.LightGray, modifier = Modifier.fillMaxHeight().width(1.dp))

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
    onSelectAllChange: (Boolean) -> Unit
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
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF5722))
            )
            Text("Semua", fontSize = 14.sp)

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Total Rp${String.format("%,.0f", totalPrice * 15000).replace(',', '.')}",
                    color = Color(0xFFFF5722),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = { },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                enabled = totalItems > 0,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text("Checkout ($totalItems)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}