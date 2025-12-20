package com.example.ecommerce.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cart_table")
data class CartEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int
)