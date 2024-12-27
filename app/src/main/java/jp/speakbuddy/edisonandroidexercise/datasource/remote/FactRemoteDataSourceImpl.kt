package jp.speakbuddy.edisonandroidexercise.datasource.remote

import jp.speakbuddy.edisonandroidexercise.api.FactApi
import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.edisonandroidexercise.di.IODispatcher
import jp.speakbuddy.network.response.BaseResponse
import jp.speakbuddy.network.service.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject

class FactRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : FactRemoteDataSource {
    override suspend fun getRemoteFact(): BaseResponse<Fact> = withContext(ioDispatcher) {
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
        return@withContext factResult
    }
}