package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.local.entity.CartEntity
import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateCartProductUseCase @Inject constructor(
    private val repository: CartRepository,
    private val productRepository: ProductRepository
) : DataStateUseCase<UpdateCartProductUseCase.Params, Cart>() {

    override suspend fun FlowCollector<DataState<Cart>>.execute(
        params: Params
    ) {
        val response = apiCall { repository.updateCart(params.cartId, params.request) }

        if (response is DataState.Success) {
            repository.clearLocalCart()

            val updatedEntities = params.request.products.map { req ->
                val productDetail = productRepository.getProduct(req.productId)

                CartEntity(
                    productId = req.productId,
                    quantity = req.quantity,
                    title = productDetail.title,
                    price = productDetail.price,
                    image = productDetail.imageUrl
                )
            }

            repository.upsertLocal(updatedEntities)
        }
        emit(response.map { it.toDomain() })
    }

    data class Params(val cartId: Int, val request: CartRequest)
}