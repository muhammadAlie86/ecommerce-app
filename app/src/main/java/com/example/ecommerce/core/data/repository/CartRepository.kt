package com.example.ecommerce.core.data.repository

import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.remote.models.response.CartResponse
import com.example.ecommerce.core.data.remote.service.CartService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    val cartService: CartService
){

    suspend fun getAllCart(): List<CartResponse> = cartService.getAllCart()

    suspend fun getCart(id: Int): CartResponse = cartService.getCart(id)
    suspend fun getCartUser(id: Int): List<CartResponse> = cartService.getCartUser(id)

    suspend fun updateCart(id: Int,request: CartRequest): CartResponse = cartService.updateCart(id,request)
    suspend fun addCart(request: CartRequest): CartResponse = cartService.addCart(request)

    suspend fun deleteCart(id: Int): String = cartService.deleteCart(id)
}


