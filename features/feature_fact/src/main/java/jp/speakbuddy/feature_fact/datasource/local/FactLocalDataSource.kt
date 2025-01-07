package jp.speakbuddy.feature_fact.datasource.local

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_network.response.BaseResponse

interface FactLocalDataSource {
    suspend fun getLocalFact(): BaseResponse<FactResponse>
    suspend fun saveFactToDataStore(factResponse: FactResponse)
    suspend fun saveFactToFavoriteDataStore(fact: FactUiData)
}
