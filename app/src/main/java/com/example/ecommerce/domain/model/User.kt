package com.example.ecommerce.domain.model


data class User(
    val id: Int,
    val email: String,
    val username: String,
    val fullName: String,
    val userAddress: UserAddress,
    val phoneNumber: String
)

data class UserAddress(
    val city: String,
    val street: String,
    val streetNumber: Int,
    val postalCode: String,
    val coordinates: Coordinates
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)