package jp.speakbuddy.edisonandroidexercise.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.repository.FactRepositoryImpl
import jp.speakbuddy.network.service.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleInjection {
    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: ApiService,
    ): FactRepository = FactRepositoryImpl(apiService)

    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher
