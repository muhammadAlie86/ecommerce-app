package com.example.ecommerce.core.data.repository

import com.example.ecommerce.core.data.local.dao.ProductDao
import com.example.ecommerce.core.data.local.entity.ProductEntity
import com.example.ecommerce.core.data.remote.models.request.ProductRequest
import com.example.ecommerce.core.data.remote.models.response.ProductResponse
import com.example.ecommerce.core.data.remote.service.ProductService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val productDao: ProductDao
) {
    suspend fun getAllProduct(): List<ProductResponse> = productService.getAllProduct()
    suspend fun getProduct( id: Int): ProductResponse = productService.getProduct(id)
    suspend fun getProductCategories(): List<ProductResponse> = productService.getProductCategories()
    suspend fun getListProductCategories(category: String): List<ProductResponse> = productService.getListProductCategories(category)
    suspend fun updateProduct(id: Int,request: ProductRequest): ProductResponse = productService.updateProduct(id,request)
    suspend fun addProduct(request: ProductRequest): ProductResponse = productService.addProduct(request)
    suspend fun deleteProduct(id: Int): String = productService.deleteProduct(id)

    suspend fun upsertProductsLocal(products: List<ProductEntity>) = productDao.upsertAllProduct(products)
    fun getProductsLocal(): Flow<List<ProductEntity>> = productDao.getAllProducts()
    fun getProductsByCategoryLocal(category: String): Flow<List<ProductEntity>> =
        productDao.getProductsByCategory(category)
}