package jp.speakbuddy.feature_fact

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.fact.FactUiState
import jp.speakbuddy.feature_fact.fact.FactViewModel
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
        expectedSavedFact: BaseResponse<Fact> = BaseResponse.Loading,
        expectedUpdateFact: BaseResponse<Fact> = BaseResponse.Loading,
    ) = FactViewModel(
        FakeFactRepository(expectedSavedFact, expectedUpdateFact),
        ioDispatcher = coroutineTest.dispatcher,
    )

    @Test
    fun `init loading state for getSavedFact`() = coroutineTest.runTest {
        // given
        val expected = FactUiState.Loading
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Loading
        )
        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFact.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals("", currentFact)
    }

    @Test
    fun `success state for getSavedFact`() = coroutineTest.runTest {
        // given
        val fact = Fact("cat", 3)
        val expected = FactUiState.Success(fact)
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Success(fact)
        )
        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFact.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals("cat", currentFact)
    }

    @Test
    fun `failed state for getSavedFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected = FactUiState.Failed(code, message)
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Failed(code, message)
        )
        // when
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFact.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals("", currentFact)
    }

    @Test
    fun `loading state for updateFact`() = coroutineTest.runTest {
        // given
        val expected = FactUiState.Loading
        val viewModel = getViewModel(
            expectedUpdateFact = BaseResponse.Loading
        )
        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFact.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals("", currentFact)
    }

    @Test
    fun `success state for updateFact`() = coroutineTest.runTest {
        // given
        val fact = Fact("cat", 3)
        val expected = FactUiState.Success(fact)
        val viewModel = getViewModel(
            expectedUpdateFact = BaseResponse.Success(fact)
        )
        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFact.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals("cat", currentFact)
    }

    @Test
    fun `failed state for updateFact`() = coroutineTest.runTest {
        // given
        val code = 404
        val message = "Not found"
        val expected = FactUiState.Failed(code, message)
        val viewModel = getViewModel(
            expectedUpdateFact = BaseResponse.Failed(code, message)
        )
        // when
        viewModel.updateFact()
        val factUiState = viewModel.factUiState.first()
        val currentFact = viewModel.currentFact.first()

        // then
        assertEquals(expected, factUiState)
        assertEquals("", currentFact)
    }

    @Test
    fun `check if fact has multiple cats`() = coroutineTest.runTest {
        // given
        val fact = Fact("cats is an animal", 10)
        val viewModel = getViewModel(
            expectedUpdateFact = BaseResponse.Success(fact)
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
        val fact = Fact("cat is an animal", 10)
        val viewModel = getViewModel(
            expectedUpdateFact = BaseResponse.Success(fact)
        )
        // when
        viewModel.updateFact()
        val result = viewModel.hasMultipleCats.first()

        // then
        assertEquals(false, result)
    }
}
