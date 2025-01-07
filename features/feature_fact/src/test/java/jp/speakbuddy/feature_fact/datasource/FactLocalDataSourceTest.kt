package jp.speakbuddy.feature_fact.datasource

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSourceImpl
import jp.speakbuddy.feature_fact.fake.testFactDataStore
import jp.speakbuddy.feature_fact.fake.testFactFavoriteListDataStore
import jp.speakbuddy.feature_fact.fake.tmpFolder
import jp.speakbuddy.lib_base.test.CoroutineTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class FactLocalDataSourceTest {
    @JvmField
    val coroutineTest = CoroutineTestExtension(true)

    private fun getFactLocalDataSource(): FactLocalDataSource {
        tmpFolder.create()
        return FactLocalDataSourceImpl(
            testFactDataStore,
            testFactFavoriteListDataStore,
            coroutineTest.dispatcher
        )
    }

    @Test
    fun `success state for getSavedFact`() = coroutineTest.runTest {
        // given
        val factResponse = FactResponse("cat", 3)
        val expected = BaseResponse.Success(factResponse)
        val dataSource = getFactLocalDataSource()

        // when
        dataSource.saveFactToDataStore(factResponse)
        val result = dataSource.getLocalFact()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `failed state but empty fact for getSavedFact`() = coroutineTest.runTest {
        // given
        val expectedFactData = "No saved fact"
        val dataSource = getFactLocalDataSource()

        // when
        dataSource.saveFactToDataStore(FactResponse("", 0))
        val result = dataSource.getLocalFact()
        val factData = (result as BaseResponse.Failed).message

        // then
        assertEquals(expectedFactData, factData)
    }
}
