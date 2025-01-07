package jp.speakbuddy.feature_fact.viewmodel

import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.ui.fact.FactUiState
import jp.speakbuddy.feature_fact.ui.fact.FactViewModel
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.lib_base.test.CoroutineTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Test

class FactViewModelTest {
    @JvmField
    val coroutineTest = CoroutineTestExtension(true)

    private fun getViewModel(
        expectedSavedFactResponse: BaseResponse<FactResponse> = BaseResponse.Loading,
        expectedUpdateFactResponse: BaseResponse<FactResponse> = BaseResponse.Loading,
    ) = FactViewModel(
        FakeFactRepository(expectedSavedFactResponse, expectedUpdateFactResponse),
        ioDispatcher = coroutineTest.dispatcher,
    )

    @Test
    fun `init loading state for getSavedFact`() = coroutineTest.runTest {
        // given
        val expected = FactUiState.Loading
        val expectedCurrentFact = FactResponse("", 0)
        val viewModel = getViewModel(
            expectedSavedFactResponse = BaseResponse.Loading
        )
        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(expectedCurrentFact, currentFact)
    }

    @Test
    fun `success state for getSavedFact`() = coroutineTest.runTest {
        // given
        val factResponse = FactResponse("cat", 3)
        val expected = FactUiState.Success(factResponse)
        val expectedCurrentFact = FactResponse("cat", 0)
        val viewModel = getViewModel(
            expectedSavedFactResponse = BaseResponse.Success(factResponse)
        )
        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(expectedCurrentFact, currentFact)
    }

    @Test
    fun `failed state for getSavedFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected = FactUiState.Failed(code, message)
        val expectedCurrentFact = FactResponse("", 0)
        val viewModel = getViewModel(
            expectedSavedFactResponse = BaseResponse.Failed(code, message)
        )
        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(expectedCurrentFact, currentFact)
    }

    @Test
    fun `loading state for updateFact`() = coroutineTest.runTest {
        // given
        val expected = FactUiState.Loading
        val expectedCurrentFact = FactResponse("", 0)
        val viewModel = getViewModel(
            expectedUpdateFactResponse = BaseResponse.Loading
        )
        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(expectedCurrentFact, currentFact)
    }

    @Test
    fun `success state for updateFact`() = coroutineTest.runTest {
        // given
        val factResponse = FactResponse("cat", 3)
        val expected = FactUiState.Success(factResponse)
        val expectedCurrentFact = FactResponse("cat", 0)
        val viewModel = getViewModel(
            expectedUpdateFactResponse = BaseResponse.Success(factResponse)
        )
        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(expectedCurrentFact, currentFact)
    }

    @Test
    fun `failed state for updateFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected = FactUiState.Failed(code, message)
        val expectedCurrentFact = FactResponse("", 0)
        val viewModel = getViewModel(
            expectedUpdateFactResponse = BaseResponse.Failed(code, message)
        )
        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFactResponse.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals(expectedCurrentFact, currentFact)
    }

    @Test
    fun `check if fact has multiple cats`() = coroutineTest.runTest {
        // given
        val factResponse = FactResponse("cats is an animal", 10)
        val viewModel = getViewModel(
            expectedUpdateFactResponse = BaseResponse.Success(factResponse)
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
        val factResponse = FactResponse("cat is an animal", 10)
        val viewModel = getViewModel(
            expectedUpdateFactResponse = BaseResponse.Success(factResponse)
        )
        // when
        viewModel.updateFact()
        val result = viewModel.hasMultipleCats.first()

        // then
        assertEquals(false, result)
    }
}
