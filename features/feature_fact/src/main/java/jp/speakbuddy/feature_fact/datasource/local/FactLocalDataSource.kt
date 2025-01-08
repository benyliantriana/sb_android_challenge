package jp.speakbuddy.feature_fact.datasource.local

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_network.response.BaseResponse

interface FactLocalDataSource {
    suspend fun getLocalFact(): BaseResponse<FactUiData>
    suspend fun saveFactToDataStore(factData: FactUiData)
    suspend fun saveFactToFavoriteDataStore(factData: FactUiData)
    suspend fun alreadyFavoriteFact(factData: FactUiData): Boolean
    suspend fun getSavedFavoriteFactList(): BaseResponse<List<FactUiData>>
}
