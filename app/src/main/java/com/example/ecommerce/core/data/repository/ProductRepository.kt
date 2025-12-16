package com.example.ecommerce.core.data.repository

import com.example.ecommerce.core.data.remote.models.request.ProductRequest
import com.example.ecommerce.core.data.remote.models.response.ProductResponse
import com.example.ecommerce.core.data.remote.service.ProductService

class ProductRepository(
    private val productService: ProductService
) {
    suspend fun getAllProduct(): List<ProductResponse> = productService.getAllProduct()


    suspend fun getProduct( id: Int): ProductResponse = productService.getProduct(id)

    suspend fun updateProduct(id: Int,request: ProductRequest): ProductResponse = productService.updateProduct(id,request)

    suspend fun addProduct(request: ProductRequest): ProductResponse = productService.addProduct(request)

    suspend fun deleteProduct(id: Int): String = productService.deleteProduct(id)
}