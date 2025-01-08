package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.repository.FactRepository
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFactRepository(
    private val savedFactResponseResponse: BaseResponse<FactUiData> = BaseResponse.Loading,
    private val updateFactResponseResponse: BaseResponse<FactUiData> = BaseResponse.Loading,
    private val savedFavoriteList: BaseResponse<List<FactUiData>> = BaseResponse.Success(emptyList()),
) : FactRepository {
    override suspend fun getSavedFact(): Flow<BaseResponse<FactUiData>> =
        flowOf(savedFactResponseResponse)

    override suspend fun updateFact(): Flow<BaseResponse<FactUiData>> =
        flowOf(updateFactResponseResponse)

    override suspend fun saveFactToFavoriteDataStore(fact: FactUiData) {}
    override suspend fun getSavedFavoriteFactList(): Flow<BaseResponse<List<FactUiData>>> =
        flowOf(savedFavoriteList)
}