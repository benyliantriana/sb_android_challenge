package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.repository.FactRepository
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFactRepository(
    private val factResponse: BaseResponse<Fact>,
) : FactRepository {
    override suspend fun getSavedFact(): Flow<BaseResponse<Fact>> =
        flowOf(factResponse)

    override suspend fun updateFact(): Flow<BaseResponse<Fact>> =
        flowOf(factResponse)
}