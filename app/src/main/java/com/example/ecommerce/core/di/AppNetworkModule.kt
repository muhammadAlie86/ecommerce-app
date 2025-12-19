package com.example.ecommerce.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.ecommerce.BuildConfig
import com.example.ecommerce.libraries.base.app.NetworkConfig
import com.example.ecommerce.libraries.network.createOkHttpClient
import com.example.ecommerce.libraries.network.interceptor.HttpRequestInterceptor
import com.example.ecommerce.libraries.utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton
import kotlin.apply
import kotlin.collections.toTypedArray


private const val BASE_URL = "base_url"
@Module
@InstallIn(SingletonComponent::class)
class AppNetworkModule {
    @Provides
    @Singleton
    fun provideNetworkConfig(): NetworkConfig {
        return AppNetworkConfig()
    }
    @Provides
    @Singleton
    @Named(value = BASE_URL)
    fun provideBaseUrl(networkConfig: NetworkConfig): String {
        return networkConfig.baseUrl()
    }

    /*@Provides
    @Singleton
    fun provideTokenAuthentication(tokenRepository: BaseTokenManger): TokenAuthenticator {
        return TokenAuthenticator(tokenRepository)
    }*/
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }



    @Provides
    @Singleton
    fun provideOkHttpClient(
        context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        httpRequestInterceptor: HttpRequestInterceptor
    ): OkHttpClient {
        val interceptors = mutableListOf<Interceptor>().apply {
            this.add(httpLoggingInterceptor)
            this.add(httpRequestInterceptor)
            if (BuildConfig.DEBUG) {
                this.add(chuckerInterceptor)
            }
        }

        return createOkHttpClient(/*
            authenticator = authenticator,*/
            interceptors = interceptors.toTypedArray(),
            context = context
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}
