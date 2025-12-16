package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.repository.ProductRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    val repository: ProductRepository
) {


}