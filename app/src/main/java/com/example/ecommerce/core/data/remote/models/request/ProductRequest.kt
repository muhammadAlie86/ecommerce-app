package com.example.ecommerce.core.data.remote.models.request

import com.squareup.moshi.Json

data class ProductRequest (

    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "price") val price: Double,
    @Json(name = "description") val description: String,
    @Json(name = "category") val category: String,
    @Json(name = "image") val image: String
)