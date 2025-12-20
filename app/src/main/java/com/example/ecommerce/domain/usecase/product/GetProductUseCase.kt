package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.mapper.toEntity
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject
import kotlin.collections.map

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) : DataStateUseCase<GetProductUseCase.Params, Product>() {

    override suspend fun FlowCollector<DataState<Product>>.execute(params: Params) {
        val response = apiCall { repository.getProduct(params.productId) }
        if (response is DataState.Success) {
            val remoteProducts = response.result
            repository.upsertProductsLocal(listOf(remoteProducts.toEntity()))
        }

        emit(response.map { it.toDomain() })
    }
    data class Params(val productId: Int)
}