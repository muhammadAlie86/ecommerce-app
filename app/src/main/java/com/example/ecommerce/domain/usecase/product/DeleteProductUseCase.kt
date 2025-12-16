package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.repository.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    val repository: ProductRepository
) {


}