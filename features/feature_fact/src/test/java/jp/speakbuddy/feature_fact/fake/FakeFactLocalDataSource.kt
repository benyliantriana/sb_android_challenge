package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.lib_network.response.BaseResponse

class FakeFactLocalDataSource(
    private val factLocalResult: BaseResponse<FactUiData> = BaseResponse.Loading,
    private val isFavoriteFactResult: Boolean = false,
    private val favoriteFactListResult: BaseResponse<List<FactUiData>> = BaseResponse.Success(
        emptyList()
    ),
) : FactLocalDataSource {
    override suspend fun getLocalFact(): BaseResponse<FactUiData> = factLocalResult

    override suspend fun saveFactToDataStore(factData: FactUiData) {}
    override suspend fun saveOrRemoveFactInFavoriteDataStore(factData: FactUiData) {}
    override suspend fun isFavoriteFact(factData: FactUiData): Boolean = isFavoriteFactResult

    override suspend fun getSavedFavoriteFactList(): BaseResponse<List<FactUiData>> =
        favoriteFactListResult
}
