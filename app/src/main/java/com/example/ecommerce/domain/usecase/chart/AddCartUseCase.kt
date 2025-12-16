package com.example.ecommerce.domain.usecase.chart

import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.repository.ChartRepository
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class AddCartUseCase @Inject constructor(
    val repository: ChartRepository
) : DataStateUseCase<AddCartUseCase.Params, Cart>(){

    override suspend fun FlowCollector<DataState<Cart>>.execute(
        params: Params
    ) {
        val response = apiCall{ repository.addChart(params.request) }
        emit(response)
    }

    data class Params(val request: CartRequest)



}