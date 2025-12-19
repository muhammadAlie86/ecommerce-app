package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    val repository: ProductRepository
) : DataStateUseCase<DeleteProductUseCase.Params, String>(){

    override suspend fun FlowCollector<DataState<String>>.execute(
        params: Params
    ) {
        val response = apiCall{ repository.deleteProduct(params.productId) }
        emit(response)
    }

    data class Params(val productId : Int)



}