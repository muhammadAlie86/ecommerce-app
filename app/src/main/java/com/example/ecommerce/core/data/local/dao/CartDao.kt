package com.example.ecommerce.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.ecommerce.core.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Upsert
    suspend fun upsertAllCart(items: List<CartEntity>)

    @Query("SELECT * FROM cart_table")
    fun getAllItems(): Flow<List<CartEntity>>

    @Query("DELETE FROM cart_table WHERE productId = :id")
    suspend fun deleteById(id: Int)

    @Query("UPDATE cart_table SET quantity = :qty WHERE productId = :id")
    suspend fun updateQuantity(id: Int, qty: Int)

    @Query("DELETE FROM cart_table")
    suspend fun deleteCart()

    @Query("SELECT SUM(quantity) FROM cart_table")
    fun getCartCount(): Flow<Int?>
}