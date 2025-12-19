package com.example.ecommerce.core.di

import com.example.ecommerce.domain.di.*
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(
    includes = [
        AuthDomain::class,
        UserDomain::class,
        ProductDomain::class,
        CartDomain::class,
    ]
)
@InstallIn(SingletonComponent::class)
class DomainModule