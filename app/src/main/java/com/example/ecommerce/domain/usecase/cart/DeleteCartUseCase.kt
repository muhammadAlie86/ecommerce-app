package com.example.ecommerce.domain.usecase.cart

import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(
    val repository: CartRepository
) : DataStateUseCase<DeleteCartUseCase.Params, String>(){

    override suspend fun FlowCollector<DataState<String>>.execute(
        params: Params
    ) {
        val response = apiCall{ repository.deleteCart(params.cartId) }
        emit(response)
    }

    data class Params(val cartId : Int)



}