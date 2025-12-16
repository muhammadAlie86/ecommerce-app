package com.example.ecommerce.core.data.remote.models.request

import com.squareup.moshi.Json

data class UserRequest (
    @Json(name = "id") val id: Int,
    @Json(name = "username") val username: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)