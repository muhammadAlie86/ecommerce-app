package com.example.ecommerce.core.data.remote.service

import com.example.ecommerce.core.data.remote.models.request.UserRequest
import com.example.ecommerce.core.data.remote.models.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @GET("users")
    suspend fun getAllUser(): List<UserResponse>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body request: UserRequest
    ): UserResponse

    @POST("users")
    suspend fun addUser(@Body request: UserRequest): UserResponse

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): String
}