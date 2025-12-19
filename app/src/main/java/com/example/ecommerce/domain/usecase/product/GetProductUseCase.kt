package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    val repository: ProductRepository
) : DataStateUseCase<GetProductUseCase.Params, Product>() {

    override suspend fun FlowCollector<DataState<Product>>.execute(
        params: Params
    ) {
        val response = apiCall { repository.getProduct(params.productId) }
        val result = response.map { it.toDomain() }
        emit(result)

    }
    data class Params(val productId: Int)
}