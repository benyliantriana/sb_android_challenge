package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSource
import jp.speakbuddy.lib_network.response.BaseResponse

class FakeFactRemoteDataSource(
    private val factRemoteResult: BaseResponse<FactUiData> = BaseResponse.Loading,
) : FactRemoteDataSource {
    override suspend fun getRemoteFact(): BaseResponse<FactUiData> = factRemoteResult
}
