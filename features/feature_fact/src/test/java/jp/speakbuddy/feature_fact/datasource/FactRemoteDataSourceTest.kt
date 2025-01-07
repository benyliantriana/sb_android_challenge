package jp.speakbuddy.feature_fact.datasource

import jp.speakbuddy.feature_fact.data.response.Fact
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSource
import jp.speakbuddy.feature_fact.fake.FakeFactRemoteDataSource
import jp.speakbuddy.lib_base.test.CoroutineTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class FactRemoteDataSourceTest {
    @JvmField
    val coroutineTest = CoroutineTestExtension(true)

    private fun getRemoteLocalDataSource(
        remoteResult: BaseResponse<Fact> = BaseResponse.Loading,
    ): FactRemoteDataSource {
        return FakeFactRemoteDataSource(remoteResult)
    }

    @Test
    fun `success state for getRemoteFact`() = coroutineTest.runTest {
        // given
        val fact = Fact("cat", 3)
        val expected = BaseResponse.Success(fact)
        val dataSource = getRemoteLocalDataSource(
            BaseResponse.Success(fact)
        )

        // when
        val result = dataSource.getRemoteFact()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `failed state for getRemoteFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected: BaseResponse<Fact> = BaseResponse.Failed(code, message)
        val dataSource = getRemoteLocalDataSource(
            BaseResponse.Failed(code, message)
        )

        // when
        val result = dataSource.getRemoteFact()

        // then
        assertEquals(expected, result)
    }
}