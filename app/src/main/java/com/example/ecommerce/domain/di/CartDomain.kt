package com.example.ecommerce.domain.di

import com.example.ecommerce.core.data.repository.CartRepository
import com.example.ecommerce.domain.usecase.chart.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ChartDomain {

    @Singleton
    @Provides
    fun provideAddChartUseCase(repository: CartRepository): AddCartUseCase {
        return AddCartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideDeleteChartUseCase(repository: CartRepository): DeleteChartUseCase {
        return DeleteChartUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllChartUseCase(repository: CartRepository): GetAllChartUseCase {
        return GetAllChartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetChartUseCase(repository: CartRepository): GetChartUseCase {
        return GetChartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideUpdateChartUseCase(repository: CartRepository): GetAllChartUseCase {
        return GetAllChartUseCase(repository)
    }





}