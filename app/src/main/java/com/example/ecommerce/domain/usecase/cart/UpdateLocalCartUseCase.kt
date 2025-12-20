package com.example.ecommerce.domain.usecase.cart

import com.example.ecommerce.core.data.remote.models.request.ProductItemRequest
import com.example.ecommerce.core.data.repository.CartRepository
import javax.inject.Inject

class UpdateLocalCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(products: List<ProductItemRequest>) {
        products.forEach { item ->
            repository.updateLocalQty(item.productId, item.quantity)
        }
    }

    suspend operator fun invoke(productId: Int, quantity: Int) {
        repository.updateLocalQty(productId, quantity)
    }
}