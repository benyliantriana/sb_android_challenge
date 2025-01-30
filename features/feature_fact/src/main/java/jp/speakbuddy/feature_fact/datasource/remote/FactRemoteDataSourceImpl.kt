package jp.speakbuddy.feature_fact.datasource.remote

import jp.speakbuddy.feature_fact.api.FactApi
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_base.di.IODispatcher
import jp.speakbuddy.lib_base.exception.getDefaultRemoteException
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject

class FactRemoteDataSourceImpl @Inject constructor(
    private val factApi: FactApi,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : FactRemoteDataSource {
    override suspend fun getRemoteFact(): BaseResponse<FactUiData> = withContext(ioDispatcher) {
        val defaultExceptionData = getDefaultRemoteException()
        var factResponseResult: BaseResponse<FactUiData> = BaseResponse.Failed(
            code = defaultExceptionData.code, message = defaultExceptionData.message
        )
        val result = factApi.getFact().awaitResponse()
        if (result.isSuccessful) {
            result.body()?.let {
                factResponseResult = BaseResponse.Success(
                    FactUiData(it.fact, it.length, false)
                )
            }
            if (result.body()?.fact.isNullOrEmpty()) {
                factResponseResult = BaseResponse.Failed(result.code(), "Fact not found!")
            }
        } else {
            factResponseResult = BaseResponse.Failed(result.code(), result.message())
        }
        return@withContext factResponseResult
    }
}
