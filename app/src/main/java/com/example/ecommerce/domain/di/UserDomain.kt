package com.example.ecommerce.domain.di

import com.example.ecommerce.core.data.repository.UserRepository
import com.example.ecommerce.domain.usecase.user.AddUserUseCase
import com.example.ecommerce.domain.usecase.user.DeleteUserUseCase
import com.example.ecommerce.domain.usecase.user.GetAllUserUseCase
import com.example.ecommerce.domain.usecase.user.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UserDomain {

    @Singleton
    @Provides
    fun provideAddUserUseCase(repository: UserRepository): AddUserUseCase {
        return AddUserUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideDeleteUserUseCase(repository: UserRepository): DeleteUserUseCase {
        return DeleteUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllUserUseCase(repository: UserRepository): GetAllUserUseCase {
        return GetAllUserUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetUserUseCase(repository: UserRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideUpdateUserUseCase(repository: UserRepository): GetAllUserUseCase {
        return GetAllUserUseCase(repository)
    }




}