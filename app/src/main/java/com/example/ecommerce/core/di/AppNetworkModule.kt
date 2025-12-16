package com.example.ecommerce.core.di

import android.content.Context
import android.os.StrictMode
import com.arranet.payajabisnis.core.di.IoDispatcher
import com.arranet.payajabisnis.datastore.DataStoreManager
import com.arranet.payajabisnis.datastore.autinterceptor.AuthInterceptor
import com.arranet.payajabisnis.datastore.autinterceptor.createAuthInterceptor
import com.arranet.payajabisnis.device.edcservice.EDCCardService
import com.arranet.payajabisnis.device.edcservice.EDCPrinterService
import com.arranet.payajabisnis.device.edcservice.EDCSerialNumber
import com.arranet.payajabisnis.device.advanc1.card.CardServiceAdvan
import com.arranet.payajabisnis.device.advanc1.printer.PrinterAdvanServices
import com.arranet.payajabisnis.device.pax.card.CardServicePax
import com.arranet.payajabisnis.device.pax.printer.PrinterA920Service
import com.arranet.payajabisnis.device.smartpeak.card.CardServiceSmartPeak
import com.arranet.payajabisnis.device.smartpeak.printer.PrinterSmarpeakServices
import com.arranet.payajabisnis.device.topwiset3.card.CardServiceTopwiseT3
import com.arranet.payajabisnis.device.topwiset3.printer.PrinterTopwiseT3Services
import com.arranet.payajabisnis.device.utils.PrinterBluetoothService
import com.arranet.payajabisnis.device.wizarpos.card.CardServiceWizar
import com.arranet.payajabisnis.device.wizarpos.printer.PrinterWizarServices
import com.arranet.payajabisnis.libraries.base.app.NetworkConfig
import com.arranet.payajabisnis.libraries.network.createConnectionCheck
import com.arranet.payajabisnis.libraries.network.createOkHttpClient
import com.arranet.payajabisnis.libraries.network.interceptor.HttpRequestInterceptor
import com.arranet.payajabisnis.libraries.utils.ConnectionCheck
import com.arranet.payajabisnis.ppob.BuildConfig
import com.arranet.payajabisnis.ppob.utils.BaseTokenManger
import com.arranet.payajabisnis.ppob.utils.TTSManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.ecommerce.libraries.base.app.NetworkConfig
import com.example.ecommerce.libraries.network.createOkHttpClient
import com.example.ecommerce.libraries.network.interceptor.HttpRequestInterceptor
import com.topwise.cloudpos.aidl.system.AidlSystem
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
}
