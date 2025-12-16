package com.example.ecommerce.core.data.remote.service

import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.remote.models.response.CartResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartService {

    @GET("charts")
    suspend fun getAllChart(): List<CartResponse>

    @GET("charts/{id}")
    suspend fun getChart(@Path("id") id: Int): CartResponse

    @PUT("charts/{id}")
    suspend fun updateChart(
        @Path("id") id: Int,
        @Body request: CartRequest
    ): CartResponse

    @POST("charts")
    suspend fun addChart(@Body request: CartRequest): CartResponse

    @DELETE("charts/{id}")
    suspend fun deleteChart(@Path("id") id: Int): String
}