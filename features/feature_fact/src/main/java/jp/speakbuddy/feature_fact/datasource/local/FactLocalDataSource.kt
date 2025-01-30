package jp.speakbuddy.feature_fact.datasource.local

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_network.response.BaseResponse

interface FactLocalDataSource {
    suspend fun getLocalFact(): BaseResponse<FactUiData>
    suspend fun saveFactToDataStore(factData: FactUiData)
    suspend fun saveOrRemoveFactInFavoriteDataStore(factData: FactUiData)
    suspend fun isFavoriteFact(factData: FactUiData): Boolean
    suspend fun getSavedFavoriteFactList(): BaseResponse<List<FactUiData>>
}
