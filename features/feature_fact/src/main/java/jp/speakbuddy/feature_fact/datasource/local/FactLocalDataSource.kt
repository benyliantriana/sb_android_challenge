package jp.speakbuddy.feature_fact.datasource.local

import jp.speakbuddy.feature_fact.data.response.Fact
import jp.speakbuddy.lib_network.response.BaseResponse

interface FactLocalDataSource {
    suspend fun getLocalFact(): BaseResponse<Fact>
    suspend fun saveFactToDataStore(fact: Fact)
}