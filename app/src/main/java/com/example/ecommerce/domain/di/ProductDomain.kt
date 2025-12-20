package com.example.ecommerce.domain.di

import com.example.ecommerce.core.data.repository.ProductRepository
import com.example.ecommerce.domain.usecase.product.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProductDomain {


    @Singleton
    @Provides
    fun provideAddProductUseCase(repository: ProductRepository): AddProductUseCase {
        return AddProductUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideDeleteProductUseCase(repository: ProductRepository): DeleteProductUseCase {
        return DeleteProductUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllProductUseCase(repository: ProductRepository): GetAllProductUseCase {
        return GetAllProductUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetProductUseCase(repository: ProductRepository): GetProductUseCase {
        return GetProductUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideUpdateProductUseCase(repository: ProductRepository): UpdateProductUseCase {
        return UpdateProductUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetLocalProductsUseCaseUseCase(repository: ProductRepository): GetLocalProductsUseCase {
        return GetLocalProductsUseCase(repository)
    }





}