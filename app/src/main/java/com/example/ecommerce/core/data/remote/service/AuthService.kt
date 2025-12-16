package com.example.ecommerce.core.data.remote.service

import com.example.ecommerce.core.data.remote.models.request.LoginRequest
import com.example.ecommerce.core.data.remote.models.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse


}