package jp.speakbuddy.feature_fact

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSourceImpl
import jp.speakbuddy.feature_fact.fake.testDataStore
import jp.speakbuddy.lib_base.test.CoroutineTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class FactLocalDataSourceTest {
    @JvmField
    @RegisterExtension
    val coroutineTest = CoroutineTestExtension(true)

    private fun getFactLocalDataSource(): FactLocalDataSource {
        return FactLocalDataSourceImpl(
            testDataStore,
            coroutineTest.dispatcher
        )
    }

    @Test
    fun `success state for getSavedFact`() = coroutineTest.runTest {
        // given
        val fact = Fact("cat", 3)
        val expected = BaseResponse.Success(fact)
        val dataSource = getFactLocalDataSource()

        // when
        dataSource.saveFactToDataStore(fact)
        val result = dataSource.getLocalFact()

        // then
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `success state but empty fact for getSavedFact`() = coroutineTest.runTest {
        // given
        val expectedFactData = "No saved fact"
        val dataSource = getFactLocalDataSource()

        // when
        // this is default value from data store if no data saved
        dataSource.saveFactToDataStore(Fact("", 0))
        val result = dataSource.getLocalFact()
        val factData = (result as BaseResponse.Success).data.fact

        // then
        Assertions.assertEquals(expectedFactData, factData)
    }
}
