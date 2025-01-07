package jp.speakbuddy.feature_fact.repository

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    suspend fun getSavedFact(): Flow<BaseResponse<FactResponse>>
    suspend fun updateFact(): Flow<BaseResponse<FactResponse>>
    suspend fun saveFactToFavoriteDataStore(fact: FactUiData)
}
