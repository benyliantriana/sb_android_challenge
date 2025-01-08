package jp.speakbuddy.feature_fact.repository

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.fake.FakeFactLocalDataSource
import jp.speakbuddy.feature_fact.fake.FakeFactRemoteDataSource
import jp.speakbuddy.lib_base.test.CoroutineTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Test

class FactRepositoryTest {
    @JvmField
    val coroutineTest = CoroutineTestExtension(true)

    private fun getRepository(
        factLocalResult: BaseResponse<FactUiData> = BaseResponse.Loading,
        isFavoriteFactResult: Boolean = false,
        favoriteFactListResult: BaseResponse<List<FactUiData>> = BaseResponse.Loading,
        factRemoteResult: BaseResponse<FactUiData> = BaseResponse.Loading,
    ): FactRepository {
        return FactRepositoryImpl(
            FakeFactLocalDataSource(factLocalResult, isFavoriteFactResult, favoriteFactListResult),
            FakeFactRemoteDataSource(factRemoteResult),
        )
    }

    @Test
    fun `init loading state for getSavedFact`() = coroutineTest.runTest {
        // given
        val expected = BaseResponse.Loading
        val repo = getRepository(
            factLocalResult = BaseResponse.Loading
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
        val factUiData = FactUiData("cat", 3, false)
        val expected = BaseResponse.Success(factUiData)
        val repo = getRepository(
            factLocalResult = BaseResponse.Success(factUiData)
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
            factLocalResult = BaseResponse.Failed(code, message)
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
            factRemoteResult = BaseResponse.Loading
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
        val factUiData = FactUiData("cat", 3, false)
        val expected = BaseResponse.Success(factUiData)
        val repo = getRepository(
            factRemoteResult = BaseResponse.Success(factUiData)
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
            factRemoteResult = BaseResponse.Failed(code, message)
        )
        // when
        val result = repo.updateFact().toList()

        // then
        assertEquals(BaseResponse.Loading, result[0])
        assertEquals(expected, result[1])
    }

    @Test
    fun `success get favorite list`() = coroutineTest.runTest {
        val factUiData = FactUiData("cat", 3, true)
        val expected = BaseResponse.Success(listOf(factUiData))
        val repo = getRepository(
            favoriteFactListResult = BaseResponse.Success(listOf(factUiData))
        )

        // when
        val result = repo.getSavedFavoriteFactList().first()

        // then
        assertEquals(expected, result)
    }
}
