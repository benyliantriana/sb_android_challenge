package jp.speakbuddy.feature_fact.repository

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    suspend fun getSavedFact(): Flow<BaseResponse<FactUiData>>
    suspend fun updateFact(): Flow<BaseResponse<FactUiData>>
    suspend fun saveFactToFavoriteDataStore(fact: FactUiData)
    suspend fun getSavedFavoriteFactList(): Flow<BaseResponse<List<FactUiData>>>
}
