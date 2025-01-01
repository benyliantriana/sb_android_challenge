package jp.speakbuddy.feature_fact.datasource.remote

import jp.speakbuddy.feature_fact.api.FactApi
import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.lib_base.di.IODispatcher
import jp.speakbuddy.lib_base.exception.getDefaultRemoteException
import jp.speakbuddy.lib_network.response.BaseResponse
import jp.speakbuddy.lib_network.service.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject

class FactRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : FactRemoteDataSource {
    override suspend fun getRemoteFact(): BaseResponse<Fact> = withContext(ioDispatcher) {
        val defaultExceptionData = getDefaultRemoteException()
        var factResult: BaseResponse<Fact> = BaseResponse.Failed(
            code = defaultExceptionData.code, message = defaultExceptionData.message
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
