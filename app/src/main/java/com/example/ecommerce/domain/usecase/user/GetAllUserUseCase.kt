package com.example.ecommerce.domain.usecase.user

import com.example.ecommerce.core.data.repository.UserRepository
import com.example.ecommerce.domain.mapper.toDomainList
import com.example.ecommerce.domain.model.User
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetAllUserUseCase @Inject constructor(
    val repository: UserRepository
) : DataStateUseCase<Unit, List<User>>(){

    override suspend fun FlowCollector<DataState<List<User>>>.execute(
        params: Unit
    ) {
        val response = apiCall{ repository.getAllUser() }
        val result = response.map { it.toDomainList() }
        emit(result)
    }
}