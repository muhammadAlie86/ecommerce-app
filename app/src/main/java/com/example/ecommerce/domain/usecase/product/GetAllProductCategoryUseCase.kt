package com.example.ecommerce.domain.usecase.product

import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.domain.mapper.toDomain
import com.example.ecommerce.domain.mapper.toDomainList
import com.example.ecommerce.domain.mapper.toEntity
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.apiCall
import com.example.ecommerce.libraries.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject
class GetAllProductCategoryUseCase @Inject constructor(
    private val repository: ProductRepository
) : DataStateUseCase<GetAllProductCategoryUseCase.Params, List<Product>>() {

    override suspend fun FlowCollector<DataState<List<Product>>>.execute(
        params: Params
    ) {
        val response = apiCall { repository.getListProductCategories(params.category) }

        if (response is DataState.Success) {

            val remoteProductsCategory = response.result
            val entities = remoteProductsCategory.map { it.toEntity() }
            repository.upsertProductsLocal(entities)
        }
        val result = response.map { it.toDomainList() }
        emit(result)
    }

    data class Params(val category: String)
}