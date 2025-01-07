package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.lib_network.response.BaseResponse

class FakeFactLocalDataSource(
    private val localResult: BaseResponse<FactResponse> = BaseResponse.Loading,
) : FactLocalDataSource {
    override suspend fun getLocalFact(): BaseResponse<FactResponse> = localResult

    override suspend fun saveFactToDataStore(factResponse: FactResponse) {}
    override suspend fun saveFactToFavoriteDataStore(fact: FactUiData) {}
}
