package jp.speakbuddy.feature_fact.di

import androidx.datastore.core.DataStore
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSourceImpl
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSource
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.feature_fact.repository.FactRepository
import jp.speakbuddy.feature_fact.repository.FactRepositoryImpl
import jp.speakbuddy.lib_datastore.FactPreference
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
    fun provideFactRepository(
        factLocalDataSource: FactLocalDataSource,
        factRemoteDataSource: FactRemoteDataSource,
    ): FactRepository =
        FactRepositoryImpl(
            factLocalDataSource,
            factRemoteDataSource
        )

    @Provides
    @Singleton
    fun provideFactLocalDataSource(
        factDataStore: DataStore<FactPreference>,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): FactLocalDataSource =
        FactLocalDataSourceImpl(
            factDataStore,
            ioDispatcher
        )

    @Provides
    @Singleton
    fun provideFactRemoteDataSource(
        apiService: ApiService,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): FactRemoteDataSource =
        FactRemoteDataSourceImpl(
            apiService,
            ioDispatcher
        )

    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}

// better move it to lib_base?? still have no idea what will be in the lib_base
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher