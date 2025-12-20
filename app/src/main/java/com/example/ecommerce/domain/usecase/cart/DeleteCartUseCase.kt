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
    private val repository: CartRepository
) : DataStateUseCase<DeleteCartUseCase.Params, Unit>() {

    override suspend fun FlowCollector<DataState<Unit>>.execute(params: Params) {

        val response = apiCall { repository.deleteCart(params.cartId) }

        if (response is DataState.Success) {
            if (params.isCheckout) {
                repository.clearLocalCart()
            } else {
                params.productId?.let { repository.deleteCartItemLocal(it) }
            }
        }
        emit(response.map { Unit })
    }

    data class Params(
        val cartId: Int,
        val isCheckout: Boolean = true,
        val productId: Int? = null
    )
}