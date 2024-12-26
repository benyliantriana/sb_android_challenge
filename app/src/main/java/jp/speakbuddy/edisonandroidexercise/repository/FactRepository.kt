package jp.speakbuddy.edisonandroidexercise.repository

import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    suspend fun getFact(): Flow<BaseResponse<Fact>>
}
