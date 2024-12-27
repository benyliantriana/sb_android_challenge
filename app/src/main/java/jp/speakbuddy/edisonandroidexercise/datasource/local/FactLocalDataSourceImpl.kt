package jp.speakbuddy.edisonandroidexercise.datasource.local

import androidx.datastore.core.DataStore
import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.edisonandroidexercise.di.IODispatcher
import jp.speakbuddy.lib_datastore.FactPreference
import jp.speakbuddy.lib_datastore.copy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class FactLocalDataSourceImpl @Inject constructor(
    private val factDataStore: DataStore<FactPreference>,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : FactLocalDataSource {
    override suspend fun getLocalFact(): Fact? = withContext(ioDispatcher) {
        var result: Fact? = null
        try {
            factDataStore.data.firstOrNull()?.let {
                result = Fact(it.fact, it.length)
            }
        } catch (ioException: IOException) {
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
