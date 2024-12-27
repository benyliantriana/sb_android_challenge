package jp.speakbuddy.edisonandroidexercise.repository

import jp.speakbuddy.edisonandroidexercise.api.FactApi
import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.edisonandroidexercise.datasource.local.FactLocalDataSource
import jp.speakbuddy.network.response.BaseResponse
import jp.speakbuddy.network.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val factLocalDataSource: FactLocalDataSource,
) : FactRepository {
    override suspend fun getFact(): Flow<BaseResponse<Fact>> = flow {
        val localFact = getSavedFact()
        localFact?.let {
            if (it.fact.isNotEmpty()) {
                emit(BaseResponse.Success(it))
            }
        }

        println("local_fact ${localFact?.fact}")

        val remoteFact = getRemoteFact()
        if (remoteFact is BaseResponse.Success) {
            saveFactToDataStore(remoteFact.data)
            emit(remoteFact)
        } else {
            if (localFact?.fact.isNullOrEmpty()) {
                emit(remoteFact)
            }
        }
    }.catch { cause ->
        when (cause) {
            is IOException -> emit(BaseResponse.Failed(502, "No Connection"))
            else -> {
                emit(BaseResponse.Failed(503, cause.message.toString()))
            }
        }
    }

    private suspend fun getRemoteFact(): BaseResponse<Fact> {
        var factResult: BaseResponse<Fact> = BaseResponse.Failed(
            code = 404, message = "Error getting new fact"
        )
        val result = apiService.service()
            .create(FactApi::class.java)
            .getFact()
            .awaitResponse()
        if (result.isSuccessful) {
            result.body()?.let {
                factResult = BaseResponse.Success(it)
            }
            if (result.body()?.fact.isNullOrEmpty()) {
                factResult = BaseResponse.Failed(result.code(), "Fact not found!")
            }
        } else {
            factResult = BaseResponse.Failed(result.code(), result.message())
        }
        return factResult
    }

    private suspend fun getSavedFact(): Fact? {
        return factLocalDataSource.getLocalFact()
    }

    private suspend fun saveFactToDataStore(fact: Fact) {
        factLocalDataSource.saveFactToDataStore(fact)
    }
}