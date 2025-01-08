package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.lib_network.response.BaseResponse

class FakeFactLocalDataSource(
    private val localResult: BaseResponse<FactUiData> = BaseResponse.Loading,
    private val alreadyFavoriteFactResult: Boolean = false,
    private val favoriteFactList: BaseResponse<List<FactUiData>> = BaseResponse.Success(emptyList()),
) : FactLocalDataSource {
    override suspend fun getLocalFact(): BaseResponse<FactUiData> = localResult

    override suspend fun saveFactToDataStore(factData: FactUiData) {}
    override suspend fun saveFactToFavoriteDataStore(factData: FactUiData) {}
    override suspend fun alreadyFavoriteFact(factData: FactUiData): Boolean =
        alreadyFavoriteFactResult

    override suspend fun getSavedFavoriteFactList(): BaseResponse<List<FactUiData>> = favoriteFactList
}
