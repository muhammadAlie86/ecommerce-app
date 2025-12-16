package com.example.ecommerce.core.di

import com.example.ecommerce.core.data.remote.service.AuthService
import com.example.ecommerce.core.data.remote.service.CartService
import com.example.ecommerce.core.data.remote.service.ProductService
import com.example.ecommerce.core.data.remote.service.UserService
import com.example.ecommerce.libraries.network.createRetrofitWithMoshi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL = "base_url"

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(
        @Named(value = BASE_URL) baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ) = createRetrofitWithMoshi<AuthService>(
        okHttpClient = okHttpClient,
        moshi = moshi,
        baseUrl = baseUrl
    )
    @Provides
    @Singleton
    fun provideChartService(
        @Named(value = BASE_URL) baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ) = createRetrofitWithMoshi<CartService>(
        okHttpClient = okHttpClient,
        moshi = moshi,
        baseUrl = baseUrl
    )
    @Provides
    @Singleton
    fun provideProductService(
        @Named(value = BASE_URL) baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ) = createRetrofitWithMoshi<ProductService>(
        okHttpClient = okHttpClient,
        moshi = moshi,
        baseUrl = baseUrl
    )
    @Provides
    @Singleton
    fun provideUserService(
        @Named(value = BASE_URL) baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ) = createRetrofitWithMoshi<UserService>(
        okHttpClient = okHttpClient,
        moshi = moshi,
        baseUrl = baseUrl
    )

}