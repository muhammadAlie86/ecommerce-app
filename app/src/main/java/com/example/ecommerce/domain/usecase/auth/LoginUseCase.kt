package com.example.ecommerce.domain.usecase.auth

import com.example.ecommerce.core.data.remote.models.request.LoginRequest
import com.example.ecommerce.core.data.remote.models.response.LoginResponse
import com.example.ecommerce.core.data.repository.AuthRepository
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    val repository: AuthRepository
) : DataStateUseCase<LoginUseCase.Params, LoginResponse>(){


    override suspend fun FlowCollector<DataState<LoginResponse>>.execute(
        params: Params
    ) {
        val response = apiCall { repository.login(params.request) }
        emit(response)
    }

    data class Params(val request: LoginRequest)


}