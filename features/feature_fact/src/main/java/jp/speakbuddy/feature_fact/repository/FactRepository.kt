package jp.speakbuddy.feature_fact.repository

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    suspend fun getSavedFact(): Flow<BaseResponse<Fact>>
    suspend fun updateFact(): Flow<BaseResponse<Fact>>
}
