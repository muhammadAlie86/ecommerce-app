package com.example.ecommerce.domain.usecase.cart

import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    val repository: CartRepository
) : DataStateUseCase<GetCartUseCase.Params, Cart>() {

    override suspend fun FlowCollector<DataState<Cart>>.execute(
        params: Params
    ) {
        val response = apiCall { repository.getCart(params.cartId) }
        val result = response.map { it.toDomain() }
        emit(result)

    }
    data class Params(val cartId: Int)
}