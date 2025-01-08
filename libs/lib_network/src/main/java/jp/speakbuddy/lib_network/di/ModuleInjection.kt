package jp.speakbuddy.lib_network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.lib_network.interceptor.ConnectivityInterceptor
import jp.speakbuddy.lib_network.interceptor.ConnectivityInterceptorImpl
import jp.speakbuddy.lib_network.service.ApiService
import jp.speakbuddy.lib_network.service.ApiServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleInjection {
    @Provides
    @Singleton
    fun provideConnectivityInterceptor(
        @ApplicationContext context: Context,
    ): ConnectivityInterceptor = ConnectivityInterceptorImpl(context)

    @Provides
    @Singleton
    fun provideApiService(
        connectivityInterceptor: ConnectivityInterceptor,
    ): ApiService = ApiServiceImpl(connectivityInterceptor)
}
