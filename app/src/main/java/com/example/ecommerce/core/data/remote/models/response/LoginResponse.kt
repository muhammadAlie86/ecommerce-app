package com.example.ecommerce.core.data.remote.models.response

import com.squareup.moshi.Json

data class LoginResponse (
    @Json(name = "token") val token: String
)