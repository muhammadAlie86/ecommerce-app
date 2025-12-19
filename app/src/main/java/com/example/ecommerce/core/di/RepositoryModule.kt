package com.example.ecommerce.core.di

import com.example.ecommerce.core.data.remote.service.AuthService
import com.example.ecommerce.core.data.remote.service.CartService
import com.example.ecommerce.core.data.remote.service.ProductService
import com.example.ecommerce.core.data.remote.service.UserService
import com.example.ecommerce.core.data.repository.AuthRepository
import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.core.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(service: AuthService) = AuthRepository(service)

    @Singleton
    @Provides
    fun provideChartRepository(service: CartService) = CartRepository(service)

    @Singleton
    @Provides
    fun provideProductRepository(service: ProductService) = ProductRepository(service)

    @Singleton
    @Provides
    fun provideUserRepository(service: UserService) = UserRepository(service)

}