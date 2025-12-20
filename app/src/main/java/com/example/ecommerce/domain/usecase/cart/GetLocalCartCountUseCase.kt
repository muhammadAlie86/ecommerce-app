package com.example.ecommerce.domain.usecase.cart

import com.example.ecommerce.core.data.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocalCartCountUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getCartCountLocal().map { it ?: 0 }
    }
}