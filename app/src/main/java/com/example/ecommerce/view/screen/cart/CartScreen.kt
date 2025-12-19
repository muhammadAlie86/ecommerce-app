package com.example.ecommerce.view.screen.cart

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.domain.model.CartItem
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.model.Rating

@Composable
fun CartScreen() {
    val mockProducts = listOf(
        Product(
            id = 1,
            title = "ZODE Clara Heels - Beige",
            price = 9.45,
            description = "High quality heels",
            category = "Shoes",
            imageUrl = "",
            ratingDetails = Rating(4.5, 100)
        ),
        Product(
            id = 2,
            title = "Pashmina Kaos Rayon Super Premium",
            price = 1.39,
            description = "Soft material hijab",
            category = "Hijab",
            imageUrl = "",
            ratingDetails = Rating(4.8, 50)
        )
    )

    val mockCart = Cart(
        id= 101,
        userId = 5,
        purchaseDate = "2023-10-12",
        products = listOf(
            CartItem(id = 1, quantity = 1),
            CartItem(id = 2, quantity = 2)
        )
    )
    CartView(
        cartResponse = mockCart,
        allProducts = mockProducts
    )
}
@Preview
@Composable
fun CartScreenPreview() {
    CartScreen()
}