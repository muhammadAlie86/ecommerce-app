package com.example.ecommerce.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommerce.core.data.local.dao.CartDao
import com.example.ecommerce.core.data.local.dao.ProductDao
import com.example.ecommerce.core.data.local.entity.CartEntity
import com.example.ecommerce.core.data.local.entity.ProductEntity

@Database(entities = [CartEntity::class, ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao

}