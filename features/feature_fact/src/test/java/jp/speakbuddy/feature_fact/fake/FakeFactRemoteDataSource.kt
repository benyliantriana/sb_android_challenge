package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.response.Fact
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSource
import jp.speakbuddy.lib_network.response.BaseResponse

class FakeFactRemoteDataSource(
    private val remoteResult: BaseResponse<Fact> = BaseResponse.Loading,
) : FactRemoteDataSource {
    override suspend fun getRemoteFact(): BaseResponse<Fact> = remoteResult
}
