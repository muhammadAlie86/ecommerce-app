package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLocalProductsByCategoryUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(category: String): Flow<List<Product>> {
        return repository.getProductsByCategoryLocal(category).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}