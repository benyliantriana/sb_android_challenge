package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.lib_network.response.BaseResponse

class FakeFactLocalDataSource(
    private val localResult: BaseResponse<Fact> = BaseResponse.Loading,
) : FactLocalDataSource {
    override suspend fun getLocalFact(): BaseResponse<Fact> = localResult

    override suspend fun saveFactToDataStore(fact: Fact) {}
}
