package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.repository.FactRepository
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFactRepository(
    private val savedFactResponse: BaseResponse<Fact> = BaseResponse.Loading,
    private val updateFactResponse: BaseResponse<Fact> = BaseResponse.Loading,
) : FactRepository {
    override suspend fun getSavedFact(): Flow<BaseResponse<Fact>> =
        flowOf(savedFactResponse)

    override suspend fun updateFact(): Flow<BaseResponse<Fact>> =
        flowOf(updateFactResponse)
}