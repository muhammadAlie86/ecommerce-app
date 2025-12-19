package com.example.ecommerce.domain.di

import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.domain.usecase.cart.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CartDomain {

    @Singleton
    @Provides
    fun provideAddCartUseCase(repository: CartRepository): AddCartUseCase {
        return AddCartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideDeleteCartUseCase(repository: CartRepository): DeleteCartUseCase {
        return DeleteCartUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllCartUseCase(repository: CartRepository): GetAllCartUseCase {
        return GetAllCartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetCartUseCase(repository: CartRepository): GetCartUseCase {
        return GetCartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideUpdateCartUseCase(repository: CartRepository): GetAllCartUseCase {
        return GetAllCartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetCartUserUseCase(repository: CartRepository): GetCartUserUseCase {
        return GetCartUserUseCase(repository)
    }





}