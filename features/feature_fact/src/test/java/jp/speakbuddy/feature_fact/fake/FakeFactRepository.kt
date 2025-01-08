package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.repository.FactRepository
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFactRepository(
    private val savedFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
    private val updateFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
    private val savedFavoriteListResult: BaseResponse<List<FactUiData>> = BaseResponse.Loading,
) : FactRepository {
    override suspend fun getSavedFact(): Flow<BaseResponse<FactUiData>> =
        flowOf(savedFactResult)

    override suspend fun updateFact(): Flow<BaseResponse<FactUiData>> =
        flowOf(updateFactResult)

    override suspend fun saveOrRemoveFactInFavoriteDataStore(fact: FactUiData) {}
    override suspend fun getSavedFavoriteFactList(): Flow<BaseResponse<List<FactUiData>>> =
        flowOf(savedFavoriteListResult)
}
