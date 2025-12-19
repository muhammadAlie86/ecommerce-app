package com.example.ecommerce.domain.usecase.cart

import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.domain.mapper.toDomainList
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetAllCartUseCase @Inject constructor(
    val repository: CartRepository
) : DataStateUseCase<Unit, List<Cart>>(){

    override suspend fun FlowCollector<DataState<List<Cart>>>.execute(
        params: Unit
    ) {
        val response = apiCall{ repository.getAllCart() }
        val result = response.map { it.toDomainList() }
        emit(result)
    }
}