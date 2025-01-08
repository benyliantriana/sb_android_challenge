package jp.speakbuddy.feature_fact.datasource

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSource
import jp.speakbuddy.feature_fact.datasource.local.FactLocalDataSourceImpl
import jp.speakbuddy.feature_fact.fake.testFactDataStore
import jp.speakbuddy.feature_fact.fake.testFactFavoriteListDataStore
import jp.speakbuddy.feature_fact.fake.tmpFolder
import jp.speakbuddy.feature_fact.helper.CoroutineTestExtension
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
        val factData = FactUiData("cat", 3, false)
        val expected = BaseResponse.Success(factData)
        val dataSource = getFactLocalDataSource()

        // when
        dataSource.saveFactToDataStore(factData)
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
        dataSource.saveFactToDataStore(FactUiData("", 0, false))
        val result = dataSource.getLocalFact()
        val factData = (result as BaseResponse.Failed).message

        // then
        assertEquals(expectedFactData, factData)
    }

    @Test
    fun `success save favorite`() = coroutineTest.runTest {
        val factData = FactUiData("cat", 3, true)
        val expected = true
        val dataSource = getFactLocalDataSource()

        // when
        dataSource.saveOrRemoveFactInFavoriteDataStore(factData)
        val result = dataSource.isFavoriteFact(factData)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `success remove favorite`() = coroutineTest.runTest {
        val factData = FactUiData("cat", 3, true)
        val expected = false
        val dataSource = getFactLocalDataSource()

        // when
        dataSource.saveOrRemoveFactInFavoriteDataStore(factData)
        dataSource.saveOrRemoveFactInFavoriteDataStore(factData.copy(
            isFavorite = false
        ))
        val result = dataSource.isFavoriteFact(factData)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `success get list favorite`() = coroutineTest.runTest {
        val factData1 = FactUiData("cat", 3, true)
        val factData2 = FactUiData("cats", 4, true)
        val expected = BaseResponse.Success(listOf(factData1, factData2))
        val dataSource = getFactLocalDataSource()

        // when
        dataSource.saveOrRemoveFactInFavoriteDataStore(factData1)
        dataSource.saveOrRemoveFactInFavoriteDataStore(factData2)

        val result = dataSource.getSavedFavoriteFactList()

        // then
        assertEquals(expected, result)
    }
}
