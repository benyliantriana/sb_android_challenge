package com.example.feature_fact.datasource.local

import androidx.datastore.core.DataStore
import com.example.feature_fact.data.Fact
import com.example.feature_fact.di.IODispatcher
import jp.speakbuddy.lib_datastore.FactPreference
import jp.speakbuddy.lib_datastore.copy
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class FactLocalDataSourceImpl @Inject constructor(
    private val factDataStore: DataStore<FactPreference>,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : FactLocalDataSource {
    override suspend fun getLocalFact(): BaseResponse<Fact> = withContext(ioDispatcher) {
        var result: BaseResponse<Fact>
        try {
            val savedFact = factDataStore.data.first()
            result = BaseResponse.Success(
                Fact(savedFact.fact, savedFact.length)
            )
        } catch (ioException: IOException) {
            result = BaseResponse.Failed(
                code = 404,
                message = ioException.message.toString()
            )
            println(ioException.message)
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
                println("savingFact: " + ioException.message)
            }
        }
    }
}
