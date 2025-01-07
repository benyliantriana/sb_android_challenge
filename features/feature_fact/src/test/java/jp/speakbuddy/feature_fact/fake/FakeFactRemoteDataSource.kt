package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSource
import jp.speakbuddy.lib_network.response.BaseResponse

class FakeFactRemoteDataSource(
    private val remoteResult: BaseResponse<FactResponse> = BaseResponse.Loading,
) : FactRemoteDataSource {
    override suspend fun getRemoteFact(): BaseResponse<FactResponse> = remoteResult
}
