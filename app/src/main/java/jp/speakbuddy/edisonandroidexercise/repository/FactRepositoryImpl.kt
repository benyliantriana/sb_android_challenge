package jp.speakbuddy.edisonandroidexercise.repository

import jp.speakbuddy.edisonandroidexercise.api.FactApi
import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.network.response.BaseResponse
import jp.speakbuddy.network.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import java.io.IOException

class FactRepositoryImpl(
    private val apiService: ApiService,
) : FactRepository {
    override suspend fun getFact(): Flow<BaseResponse<Fact>> = flow {
        val result = apiService.service()
            .create(FactApi::class.java)
            .getFact()
            .awaitResponse()
        if (result.isSuccessful) {
            result.body()?.let {
                emit(BaseResponse.Success(it))
            }
            if (result.body()?.fact.isNullOrEmpty()) {
                emit(BaseResponse.Failed(result.code(), "Fact not found!"))
            }
        } else {
            emit(BaseResponse.Failed(result.code(), result.message()))
        }
    }.catch { cause ->
        when (cause) {
            is IOException -> emit(BaseResponse.Failed(502, "No Connection"))
            else -> {
                emit(BaseResponse.Failed(503, cause.message.toString()))
            }
        }
    }
}