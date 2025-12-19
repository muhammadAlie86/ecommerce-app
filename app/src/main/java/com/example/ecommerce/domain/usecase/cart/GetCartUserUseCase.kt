package com.example.ecommerce.domain.usecase.cart

import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.mapper.toDomainList
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetCartUserUseCase @Inject constructor(
    val repository: CartRepository
) : DataStateUseCase<GetCartUserUseCase.Params, List<Cart>>() {

    override suspend fun FlowCollector<DataState<List<Cart>>>.execute(
        params: Params
    ) {
        val response = apiCall { repository.getCartUser(params.cartId) }
        val result = response.map { it.toDomainList() }
        emit(result)

    }
    data class Params(val cartId: Int)
}