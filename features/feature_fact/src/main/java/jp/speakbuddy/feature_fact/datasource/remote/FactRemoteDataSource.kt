package jp.speakbuddy.feature_fact.datasource.remote

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.network.response.BaseResponse

interface FactRemoteDataSource {
    suspend fun getRemoteFact(): BaseResponse<Fact>
}
