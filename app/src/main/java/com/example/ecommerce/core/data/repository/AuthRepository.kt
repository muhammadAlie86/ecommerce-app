package com.example.ecommerce.core.data.repository

import com.example.ecommerce.core.data.remote.models.request.LoginRequest
import com.example.ecommerce.core.data.remote.service.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    val authService: AuthService

){
    suspend fun login(loginRequest: LoginRequest) = authService.login(loginRequest)
}