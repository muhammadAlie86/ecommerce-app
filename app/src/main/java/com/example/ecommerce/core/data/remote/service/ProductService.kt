package com.example.ecommerce.core.data.remote.service

import com.example.ecommerce.core.data.remote.models.request.ProductRequest
import com.example.ecommerce.core.data.remote.models.response.ProductResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {

    @GET("products")
    suspend fun getAllProduct(): List<ProductResponse>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductResponse

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body request: ProductRequest
    ): ProductResponse

    @POST("products")
    suspend fun addProduct(@Body request: ProductRequest): ProductResponse

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): String
}