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

    @GET("carts")
    suspend fun getAllCart(): List<CartResponse>

    @GET("carts/{id}")
    suspend fun getCart(@Path("id") id: Int): CartResponse

    @GET("carts/user/{id}")
    suspend fun getCartUser(@Path("id") id: Int): List<CartResponse>

    @PUT("carts/{id}")
    suspend fun updateCart(
        @Path("id") id: Int,
        @Body request: CartRequest
    ): CartResponse

    @POST("carts")
    suspend fun addCart(@Body request: CartRequest): CartResponse

    @DELETE("charts/{id}")
    suspend fun deleteCart(@Path("id") id: Int): String
}