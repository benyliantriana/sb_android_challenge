package jp.speakbuddy.feature_fact.datasource.local

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.network.response.BaseResponse

interface FactLocalDataSource {
    suspend fun getLocalFact(): BaseResponse<Fact>
    suspend fun saveFactToDataStore(fact: Fact)
}