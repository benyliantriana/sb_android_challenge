package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.repository.FactRepository
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFactRepository(
    private val savedFactResponseResponse: BaseResponse<FactResponse> = BaseResponse.Loading,
    private val updateFactResponseResponse: BaseResponse<FactResponse> = BaseResponse.Loading,
) : FactRepository {
    override suspend fun getSavedFact(): Flow<BaseResponse<FactResponse>> =
        flowOf(savedFactResponseResponse)

    override suspend fun updateFact(): Flow<BaseResponse<FactResponse>> =
        flowOf(updateFactResponseResponse)

    override suspend fun saveFactToFavoriteDataStore(fact: FactUiData) {}
}