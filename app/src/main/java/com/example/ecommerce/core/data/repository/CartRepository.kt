package com.example.ecommerce.core.data.repository

import com.example.ecommerce.core.data.local.dao.CartDao
import com.example.ecommerce.core.data.local.entity.CartEntity
import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.remote.models.response.CartResponse
import com.example.ecommerce.core.data.remote.service.CartService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartService: CartService,
    private val cartDao: CartDao
){

    suspend fun getAllCart(): List<CartResponse> = cartService.getAllCart()

    suspend fun getCart(id: Int): CartResponse = cartService.getCart(id)
    suspend fun getCartUser(id: Int): List<CartResponse> = cartService.getCartUser(id)

    suspend fun updateCart(id: Int,request: CartRequest): CartResponse = cartService.updateCart(id,request)
    suspend fun addCart(request: CartRequest): CartResponse = cartService.addCart(request)

    suspend fun deleteCart(id: Int): CartResponse = cartService.deleteCart(id)

    //local
    fun getLocalCarts(): Flow<List<CartEntity>> = cartDao.getAllItems()
    suspend fun upsertLocal(items: List<CartEntity>) = cartDao.upsertAllCart(items)
    suspend fun updateLocalQty(id: Int, qty: Int) = cartDao.updateQuantity(id, qty)
    suspend fun deleteCartItemLocal(id: Int) = cartDao.deleteById(id)
    suspend fun clearLocalCart() = cartDao.deleteCart()

    fun getCartCountLocal() = cartDao.getCartCount()
}


