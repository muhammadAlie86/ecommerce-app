package com.example.ecommerce.domain.usecase.user

import com.example.ecommerce.core.data.remote.models.request.UserRequest
import com.example.ecommerce.core.data.repository.UserRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.model.User
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    val repository: UserRepository
) : DataStateUseCase<AddUserUseCase.Params, User>(){

    override suspend fun FlowCollector<DataState<User>>.execute(
        params: Params
    ) {
        val response = apiCall{ repository.addUser(params.request) }
        val result = response.map { it.toDomain() }
        emit(result)
    }

    data class Params(val request: UserRequest)



}