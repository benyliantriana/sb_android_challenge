package jp.speakbuddy.feature_fact

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.datasource.remote.FactRemoteDataSource
import jp.speakbuddy.feature_fact.fake.FakeFactRemoteDataSource
import jp.speakbuddy.lib_base.test.CoroutineTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class FactRemoteDataSourceTest {
    @JvmField
    @RegisterExtension
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
        Assertions.assertEquals(expected, result)
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
        Assertions.assertEquals(expected, result)
    }
}