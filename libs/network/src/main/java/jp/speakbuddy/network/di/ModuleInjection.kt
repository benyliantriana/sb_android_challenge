package jp.speakbuddy.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.network.interceptor.ConnectivityInterceptor
import jp.speakbuddy.network.interceptor.ConnectivityInterceptorImpl
import jp.speakbuddy.network.service.ApiService
import jp.speakbuddy.network.service.ApiServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleInjection {
    @Provides
    @Singleton
    fun provideConnectivityInterceptor(
        @ApplicationContext context: Context
    ): ConnectivityInterceptor = ConnectivityInterceptorImpl(context)

    @Provides
    @Singleton
    fun provideApiService(
        connectivityInterceptor: ConnectivityInterceptor
    ): ApiService = ApiServiceImpl(connectivityInterceptor)
}
