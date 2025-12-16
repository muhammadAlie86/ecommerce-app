package com.example.ecommerce.core.data.remote.models.response

import com.squareup.moshi.Json


data class CartProductResponse(
    @Json(name = "productId") val productId: Int,
    @Json(name = "quantity") val quantity: Int
)

data class CartResponse(
    @Json(name = "id") val cartId: Int,
    @Json(name = "userId") val userId: Int,
    @Json(name = "date") val purchaseDate: String,
    @Json(name = "products") val products: List<CartProductResponse>
)