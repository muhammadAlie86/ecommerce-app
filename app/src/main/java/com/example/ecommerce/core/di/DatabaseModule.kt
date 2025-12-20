package com.example.ecommerce.core.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import com.example.ecommerce.core.data.local.dao.CartDao
import com.example.ecommerce.core.data.local.dao.ProductDao
import com.example.ecommerce.core.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        System.loadLibrary("sqlcipher")

        val passphrase = "password_kamu".toByteArray()
        val factory = SupportOpenHelperFactory(passphrase)

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "secure_shop_db"
        )
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao {
        return db.cartDao()
    }
    @Provides
    @Singleton
    fun provideProductDao(db: AppDatabase): ProductDao {
        return db.productDao()
    }
}