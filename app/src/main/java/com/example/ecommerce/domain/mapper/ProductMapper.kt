package com.example.ecommerce.domain.mapper

import com.example.ecommerce.core.data.local.entity.ProductEntity
import com.example.ecommerce.core.data.remote.models.response.ProductResponse
import com.example.ecommerce.core.data.remote.models.response.RatingResponse
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.model.Rating


fun ProductResponse.toDomain(): Product {
    return Product(
        id = this.productId,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        imageUrl = this.imageUrl,
        ratingDetails = this.ratingDetails.toDomain()
    )
}

fun RatingResponse.toDomain(): Rating {
    return Rating(
        rate = this.rate,
        count = this.count
    )
}

fun List<ProductResponse>.toDomainList(): List<Product> {
    return this.map { it.toDomain() }
}
fun ProductResponse.toEntity(): ProductEntity {
    return ProductEntity(
        productId = this.productId,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        image = this.imageUrl,
        ratingRate = this.ratingDetails.rate,
        ratingCount = this.ratingDetails.count
    )
}
fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.productId,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        imageUrl = this.image,
        ratingDetails = Rating(
            rate = this.ratingRate,
            count = this.ratingCount
        )
    )
}