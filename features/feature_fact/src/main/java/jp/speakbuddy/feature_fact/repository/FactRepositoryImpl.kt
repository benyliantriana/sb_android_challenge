package jp.speakbuddy.feature_fact.repository

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSource
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factLocalDataSource: FactLocalDataSource,
    private val factRemoteDataSource: FactRemoteDataSource,
) : FactRepository {
    override suspend fun getSavedFact(): Flow<BaseResponse<Fact>> = flow {
        emit(BaseResponse.Loading)
        emit(getStoredFact())
    }

    override suspend fun updateFact(): Flow<BaseResponse<Fact>> = flow {
        emit(BaseResponse.Loading)
        val remoteFact = getRemoteFact()
        if (remoteFact is BaseResponse.Success) {
            storeFact(remoteFact.data)
        }
        emit(remoteFact)
    }.catch { cause ->
        when (cause) {
            is IOException -> emit(BaseResponse.Failed(502, "No Connection"))
            else -> {
                emit(BaseResponse.Failed(503, cause.message.toString()))
            }
        }
    }

    private suspend fun getRemoteFact(): BaseResponse<Fact> {
        return factRemoteDataSource.getRemoteFact()
    }

    private suspend fun getStoredFact(): BaseResponse<Fact> {
        return factLocalDataSource.getLocalFact()
    }

    private suspend fun storeFact(fact: Fact) {
        factLocalDataSource.saveFactToDataStore(fact)
    }
}