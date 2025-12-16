package com.example.ecommerce.core.data.remote.models.response

import com.squareup.moshi.Json

data class UserResponse(
    val id: Int,
    @Json(name = "email") val email: String,
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String,
    @Json(name = "name") val name: NameResponse,
    @Json(name = "address") val address: AddressResponse,
    @Json(name = "phone") val phone: String
)

data class NameResponse(
    @Json(name = "firstname") val firstname: String,
    @Json(name = "lastname") val lastname: String
)

data class AddressResponse(
    @Json(name = "city") val city: String,
    @Json(name = "street") val street: String,
    @Json(name = "number") val number: Int,
    @Json(name = "zipcode") val zipcode: String,
    @Json(name = "geolocation") val geolocationResponse: GeolocationResponse
)

data class GeolocationResponse(
    @Json(name = "lat") val lat: String,
    @Json(name = "long") val long: String
)