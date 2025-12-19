package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.remote.models.request.ProductRequest
import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    val repository: ProductRepository
) : DataStateUseCase<AddProductUseCase.Params, Product>(){

    override suspend fun FlowCollector<DataState<Product>>.execute(
        params: Params
    ) {
        val response = apiCall{ repository.addProduct(params.request) }
        val result = response.map { it.toDomain() }
        emit(result)
    }

    data class Params(val request: ProductRequest)



}