package com.example.ecommerce.core.data.remote.models.request

import com.squareup.moshi.Json

data class CartRequest (
    @Json(name = "id") val id: Int,
    @Json(name = "userId") val userId: Int,
    @Json(name = "products") val products: List<ProductItem>
)
data class ProductItem(

    @Json(name = "id") val productId: Int,
    @Json(name = "title") val title: String,
    @Json(name = "price") val price: Double,
    @Json(name = "category") val category: String,
    @Json(name = "description") val description: String,
    @Json(name = "image") val image: String,


)