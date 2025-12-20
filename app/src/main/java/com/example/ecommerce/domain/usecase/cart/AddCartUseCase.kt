package com.example.ecommerce.domain.usecase.cart

import com.example.ecommerce.core.data.local.entity.CartEntity
import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class AddCartUseCase @Inject constructor(
    private val repository: CartRepository
) : DataStateUseCase<AddCartUseCase.Params, Cart>() {

    override suspend fun FlowCollector<DataState<Cart>>.execute(params: Params) {
        val response = apiCall { repository.addCart(params.request) }

        if (response is DataState.Success) {
            val entities = params.request.products.map {
                CartEntity(
                    productId = it.productId,
                    quantity = it.quantity,
                    title = "", price = 0.0, image = ""
                )
            }
            repository.upsertLocal(entities)
        }
        emit(response.map { it.toDomain() })
    }
    data class Params(val request: CartRequest)
}