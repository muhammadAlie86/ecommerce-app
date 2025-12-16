package com.example.ecommerce.domain.di

import com.example.ecommerce.core.data.repository.ChartRepository
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
    fun provideAddChartUseCase(repository: ChartRepository): AddCartUseCase {
        return AddCartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideDeleteChartUseCase(repository: ChartRepository): DeleteChartUseCase {
        return DeleteChartUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllChartUseCase(repository: ChartRepository): GetAllChartUseCase {
        return GetAllChartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetChartUseCase(repository: ChartRepository): GetChartUseCase {
        return GetChartUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideUpdateChartUseCase(repository: ChartRepository): GetAllChartUseCase {
        return GetAllChartUseCase(repository)
    }





}