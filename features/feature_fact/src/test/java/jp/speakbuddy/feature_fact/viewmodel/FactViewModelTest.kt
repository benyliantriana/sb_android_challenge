package jp.speakbuddy.feature_fact.viewmodel

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.feature_fact.helper.CoroutineTestExtension
import jp.speakbuddy.feature_fact.ui.fact.FactUiState
import jp.speakbuddy.feature_fact.ui.fact.FactViewModel
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Test

class FactViewModelTest {
    @JvmField
    val coroutineTest = CoroutineTestExtension(true)

    private fun getViewModel(
        savedFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
        updateFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
    ) = FactViewModel(
        FakeFactRepository(savedFactResult, updateFactResult),
        ioDispatcher = coroutineTest.dispatcher,
    )

    @Test
    fun `init loading state for getSavedFact`() = coroutineTest.runTest {
        // given
        val expected = FactUiState.Loading
        val factUiData = FactUiData("", 0, false)
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Loading
        )

        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(factUiData, currentFact)
    }

    @Test
    fun `success state for getSavedFact`() = coroutineTest.runTest {
        // given
        val factUiData = FactUiData("cat", 3, false)
        val expected = FactUiState.Success(factUiData)
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Success(factUiData)
        )

        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(factUiData, currentFact)
    }

    @Test
    fun `failed state for getSavedFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected = FactUiState.Failed(code, message)
        val factUiData = FactUiData("", 0, false)
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Failed(code, message)
        )

        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(factUiData, currentFact)
    }

    @Test
    fun `loading state for updateFact`() = coroutineTest.runTest {
        // given
        val expected = FactUiState.Loading
        val factUiData = FactUiData("", 0, false)
        val viewModel = getViewModel(
            updateFactResult = BaseResponse.Loading
        )

        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(factUiData, currentFact)
    }

    @Test
    fun `success state for updateFact`() = coroutineTest.runTest {
        // given
        val factUiData = FactUiData("cat", 3, false)
        val expected = FactUiState.Success(factUiData)
        val viewModel = getViewModel(
            updateFactResult = BaseResponse.Success(factUiData)
        )

        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(factUiData, currentFact)
    }

    @Test
    fun `failed state for updateFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected = FactUiState.Failed(code, message)
        val factUiData = FactUiData("", 0, false)
        val viewModel = getViewModel(
            updateFactResult = BaseResponse.Failed(code, message)
        )

        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(factUiData, currentFact)
    }

    @Test
    fun `check if fact has multiple cats`() = coroutineTest.runTest {
        // given
        val factUiData = FactUiData("cats is an animal", 10, false)
        val viewModel = getViewModel(
            updateFactResult = BaseResponse.Success(factUiData)
        )

        // when
        viewModel.updateFact()
        val result = viewModel.hasMultipleCats.first()

        // then
        assertEquals(true, result)
    }

    @Test
    fun `check if fact doesn't has multiple cats`() = coroutineTest.runTest {
        // given
        val factUiData = FactUiData("cat is an animal", 10, false)
        val viewModel = getViewModel(
            updateFactResult = BaseResponse.Success(factUiData)
        )

        // when
        viewModel.updateFact()
        val result = viewModel.hasMultipleCats.first()

        // then
        assertEquals(false, result)
    }
}
