package jp.speakbuddy.feature_fact.datasource.local

import androidx.datastore.core.DataStore
import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.lib_base.di.IODispatcher
import jp.speakbuddy.lib_base.exception.localExceptionHandler
import jp.speakbuddy.lib_datastore.FactPreference
import jp.speakbuddy.lib_datastore.copy
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class FactLocalDataSourceImpl @Inject constructor(
    private val factDataStore: DataStore<FactPreference>,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : FactLocalDataSource {
    companion object {
        private const val NO_SAVED_FACT = "No saved fact"
    }

    override suspend fun getLocalFact(): BaseResponse<Fact> = withContext(ioDispatcher) {
        var result: BaseResponse<Fact>
        try {
            val savedFact = factDataStore.data.first()
            val factDescription = savedFact.fact.ifEmpty { NO_SAVED_FACT }
            result = BaseResponse.Success(
                Fact(factDescription, savedFact.length)
            )
        } catch (ioException: IOException) {
            val exceptionData = localExceptionHandler(ioException)
            result = BaseResponse.Failed(
                code = exceptionData.code,
                message = exceptionData.message
            )
        }

        return@withContext result
    }

    override suspend fun saveFactToDataStore(fact: Fact) {
        withContext(ioDispatcher) {
            try {
                factDataStore.updateData {
                    it.copy {
                        this.fact = fact.fact
                        this.length = fact.length
                    }
                }
            } catch (ioException: IOException) {
                // need a better approach for this
                throw ioException
            }
        }
    }
}
