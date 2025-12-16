package com.example.ecommerce.core.data.repository

import com.example.ecommerce.core.data.remote.models.request.UserRequest
import com.example.ecommerce.core.data.remote.models.response.UserResponse
import com.example.ecommerce.core.data.remote.service.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    val userService: UserService

){
    suspend fun getAllUser(): List<UserResponse> = userService.getAllUser()

    suspend fun getUser( id: Int): UserResponse = userService.getUser(id)

    suspend fun updateUser( id: Int, request: UserRequest): UserResponse = userService.updateUser(id,request)

    suspend fun addUser(request: UserRequest): UserResponse = userService.addUser(request)

    suspend fun deleteUser(id: Int): String = userService.deleteUser(id)
}