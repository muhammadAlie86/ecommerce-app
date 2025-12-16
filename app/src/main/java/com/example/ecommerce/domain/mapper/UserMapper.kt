package com.example.ecommerce.domain.mapper

import com.example.ecommerce.core.data.remote.models.response.AddressResponse
import com.example.ecommerce.core.data.remote.models.response.GeolocationResponse
import com.example.ecommerce.core.data.remote.models.response.UserResponse
import com.example.ecommerce.domain.model.Coordinates
import com.example.ecommerce.domain.model.User
import com.example.ecommerce.domain.model.UserAddress

fun GeolocationResponse.toDomain(): Coordinates {
    return Coordinates(
        latitude = this.lat,
        longitude = this.long
    )
}

fun AddressResponse.toDomain(coordinates: Coordinates): UserAddress {
    return UserAddress(
        city = this.city,
        street = this.street,
        streetNumber = this.number,
        postalCode = this.zipcode,
        coordinates = coordinates
    )
}

fun UserResponse.toDomain(): User {

    val domainCoordinates = this.address.geolocationResponse.toDomain()
    val domainAddress = this.address.toDomain(domainCoordinates)

    return User(
        id = this.id,
        email = this.email,
        username = this.username,
        fullName = "${this.name.firstname} ${this.name.lastname}",
        userAddress = domainAddress,
        phoneNumber = this.phone
    )
}