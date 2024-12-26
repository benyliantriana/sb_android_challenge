package jp.speakbuddy.lib_datastore.di

import FactSerializer
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.lib_datastore.FactPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleInjection {
    companion object {
        private const val FACT_PREFERENCE_NAME = "fact_preference.pb"
    }

    @Provides
    @Singleton
    fun provideProtoDataStore(
        @ApplicationContext context: Context,
    ): DataStore<FactPreference> = DataStoreFactory.create(
        serializer = FactSerializer,
        produceFile = {
            context.dataStoreFile(FACT_PREFERENCE_NAME)
        },
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    )
}
