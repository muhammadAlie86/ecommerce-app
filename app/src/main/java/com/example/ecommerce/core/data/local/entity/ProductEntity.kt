package com.example.ecommerce.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val ratingRate: Double,
    val ratingCount: Int
)