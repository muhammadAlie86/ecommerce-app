package com.example.ecommerce.core.data.remote.models.common

data class CartItemUIModel(
    val productId: Int,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val category: String,
    val isSelected: Boolean = false
)