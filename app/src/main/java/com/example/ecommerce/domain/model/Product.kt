package com.example.ecommerce.domain.model


data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val imageUrl: String,
    val ratingDetails: Rating
)
data class Rating(
    val rate: Double,
    val count: Int
)

