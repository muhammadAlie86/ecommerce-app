package com.example.ecommerce.domain.model



data class Cart(
    val id: Int,
    val userId: Int,
    val purchaseDate: String,
    val products: List<CartItem>
)

data class CartItem(
    val id: Int,
    val quantity: Int
)
