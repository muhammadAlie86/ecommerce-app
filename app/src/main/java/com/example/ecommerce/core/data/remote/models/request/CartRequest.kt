package com.example.ecommerce.core.data.remote.models.request

import com.squareup.moshi.Json

data class CartRequest(
    @Json(name = "userId") val userId: Int,
    @Json(name = "date") val date: String,
    @Json(name = "products") val products: List<ProductItemRequest>
)
data class ProductItemRequest(
    @Json(name = "productId") val productId: Int,
    @Json(name = "quantity") val quantity: Int
)