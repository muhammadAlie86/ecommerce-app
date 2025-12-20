package com.example.ecommerce.domain.usecase.cart

import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.domain.mapper.mapToCartEntity
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.mapper.toDomainList
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject
class GetCartUserUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : DataStateUseCase<GetCartUserUseCase.Params, List<Cart>>() {

    override suspend fun FlowCollector<DataState<List<Cart>>>.execute(params: Params) {

        val cartResponse = apiCall { cartRepository.getCartUser(params.userId) }
        val productResponse = apiCall { productRepository.getAllProduct() }

        if (cartResponse is DataState.Success && productResponse is DataState.Success) {
            val productList = productResponse.result

            val entities = cartResponse.result.flatMap { remoteCart ->
                remoteCart.products.map { remoteProduct ->
                    val detail = productList.find { it.productId == remoteProduct.productId }

                    mapToCartEntity(remoteProduct, detail)
                }
            }

            cartRepository.upsertLocal(entities)
        }
        val result = cartResponse.map { it.toDomainList() }
        emit(result)
    }

    data class Params(val userId: Int)
}