package jp.speakbuddy.edisonandroidexercise.ui.fake

import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFactRepository(
    private val factResponse: BaseResponse<Fact>
): FactRepository {
    override suspend fun getFact(): Flow<BaseResponse<Fact>> = flowOf(factResponse)
}