package com.example.ecommerce.domain.mapper

import com.example.ecommerce.core.data.local.entity.CartEntity
import com.example.ecommerce.core.data.remote.models.response.CartProductResponse
import com.example.ecommerce.core.data.remote.models.response.CartResponse
import com.example.ecommerce.core.data.remote.models.response.ProductResponse
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.domain.model.CartItem


fun CartResponse.toDomain(): Cart {
    val domainProducts = this.products.map { it.toDomain() }

    return Cart(
        id = this.cartId,
        userId = this.userId,
        purchaseDate = this.purchaseDate,
        products = domainProducts
    )
}

fun CartProductResponse.toDomain(): CartItem {
    return CartItem(
        id = this.productId,
        quantity = this.quantity
    )
}

fun List<CartResponse>.toDomainList(): List<Cart> {
    return this.map { it.toDomain() }
}
fun mapToCartEntity(
    cartProduct: CartProductResponse,
    productDetail: ProductResponse?
): CartEntity {
    return CartEntity(
        productId = cartProduct.productId,
        quantity = cartProduct.quantity,
        title = productDetail?.title ?: "Unknown",
        price = productDetail?.price ?: 0.0,
        image = productDetail?.imageUrl ?: ""
    )
}