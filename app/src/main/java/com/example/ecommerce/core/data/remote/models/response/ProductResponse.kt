package com.example.ecommerce.core.data.remote.models.response

import com.squareup.moshi.Json


data class RatingResponse(
    @Json(name = "rate") val rate: Double,
    @Json(name = "count") val count: Int
)

data class ProductResponse(
    @Json(name = "id") val productId: Int,
    @Json(name = "title") val title: String,
    @Json(name = "price") val price: Double,
    @Json(name = "description") val description: String,
    @Json(name = "category") val category: String,
    @Json(name = "image") val imageUrl: String,
    @Json(name = "rating") val ratingDetails: RatingResponse
)