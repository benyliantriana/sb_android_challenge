package jp.speakbuddy.edisonandroidexercise.datasource.local

import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.network.response.BaseResponse

interface FactLocalDataSource {
    suspend fun getLocalFact(): BaseResponse<Fact>
    suspend fun saveFactToDataStore(fact: Fact)
}