package jp.speakbuddy.feature_fact.repository

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.fake.FakeFactLocalDataSource
import jp.speakbuddy.feature_fact.fake.FakeFactRemoteDataSource
import jp.speakbuddy.lib_base.test.CoroutineTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Test

class FactRepositoryTest {
    @JvmField
    val coroutineTest = CoroutineTestExtension(true)

    private fun getRepository(
        localResult: BaseResponse<FactUiData> = BaseResponse.Loading,
        remoteResult: BaseResponse<FactUiData> = BaseResponse.Loading,
    ): FactRepository {
        return FactRepositoryImpl(
            FakeFactLocalDataSource(localResult),
            FakeFactRemoteDataSource(remoteResult),
        )
    }

    @Test
    fun `init loading state for getSavedFact`() = coroutineTest.runTest {
        // given
        val expected = BaseResponse.Loading
        val repo = getRepository(
            localResult = BaseResponse.Loading
        )
        // when
        var result: BaseResponse<FactUiData>? = null
        repo.getSavedFact().collect {
            result = it
        }

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `success state for getSavedFact`() = coroutineTest.runTest {
        // given
        val factResponse = FactUiData("cat", 3, false)
        val expected = BaseResponse.Success(factResponse)
        val repo = getRepository(
            localResult = BaseResponse.Success(factResponse)
        )
        // when
        val result = repo.getSavedFact().toList()

        // then
        assertEquals(BaseResponse.Loading, result[0])
        assertEquals(expected, result[1])
    }

    @Test
    fun `failed state for getSavedFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected: BaseResponse<FactResponse> = BaseResponse.Failed(code, message)
        val repo = getRepository(
            localResult = BaseResponse.Failed(code, message)
        )
        // when
        val result = repo.getSavedFact().toList()

        // then
        assertEquals(BaseResponse.Loading, result[0])
        assertEquals(expected, result[1])
    }

    @Test
    fun `init loading state for updateFact`() = coroutineTest.runTest {
        // given
        val expected = BaseResponse.Loading
        val repo = getRepository(
            remoteResult = BaseResponse.Loading
        )
        // when
        var result: BaseResponse<FactUiData>? = null
        repo.updateFact().collect {
            result = it
        }

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `success state for updateFact`() = coroutineTest.runTest {
        // given
        val factResponse = FactUiData("cat", 3, false)
        val expected = BaseResponse.Success(factResponse)
        val repo = getRepository(
            remoteResult = BaseResponse.Success(factResponse)
        )
        // when
        val result = repo.updateFact().toList()

        // then
        assertEquals(BaseResponse.Loading, result[0])
        assertEquals(expected, result[1])
    }

    @Test
    fun `failed state for updateFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected: BaseResponse<FactResponse> = BaseResponse.Failed(code, message)
        val repo = getRepository(
            remoteResult = BaseResponse.Failed(code, message)
        )
        // when
        val result = repo.updateFact().toList()

        // then
        assertEquals(BaseResponse.Loading, result[0])
        assertEquals(expected, result[1])
    }
}
