package jp.speakbuddy.feature_fact.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.feature_fact.fake.FakeFactViewModel
import jp.speakbuddy.feature_fact.ui.fact.FactScreen
import jp.speakbuddy.lib_base.test.ComposableTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FactScreenTest : ComposableTestExtension() {

    private fun getViewModel(
        expectedSavedFact: BaseResponse<Fact> = BaseResponse.Loading,
        expectedUpdateFact: BaseResponse<Fact> = BaseResponse.Loading,
    ) = FakeFactViewModel(
        FakeFactRepository(expectedSavedFact, expectedUpdateFact),
    )

    @Test
    fun `test loading state FactScreen`() = runTest {
        val viewModel = getViewModel(expectedSavedFact = BaseResponse.Loading)

        composeTestRule.setContent {
            FactScreen(viewModel)
        }

        composeTestRule.onNode(hasText("Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Loading..")).assertIsDisplayed()
    }

    @Test
    fun `test success state no multiple cat`() = runTest {
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Success(Fact("some fact", 0)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel)
        }
        composeTestRule.onNode(hasText("Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertDoesNotExist()
    }

    @Test
    fun `test success state has multiple cats`() = runTest {
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Success(Fact("some cats", 0)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel)
        }
        composeTestRule.onNode(hasText("Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some cats")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertIsDisplayed()
    }

    @Test
    fun `test success state has length more than 100`() = runTest {
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Success(Fact("some cats", 102)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel)
        }
        composeTestRule.onNode(hasText("Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some cats")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Length: 102")).assertIsDisplayed()
    }

    @Test
    fun `test success state has length below 100`() = runTest {
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Success(Fact("some cats", 5)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel)
        }
        composeTestRule.onNode(hasText("Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some cats")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Length: 5")).assertDoesNotExist()
    }

    @Test
    fun `test success state from remote and replace local value`() = runTest {
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Success(Fact("some fact", 0)),
            expectedUpdateFact = BaseResponse.Success(Fact("some another fact", 102)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel)
        }
        composeTestRule.onNode(hasText("Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasText("some another fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertDoesNotExist()
        composeTestRule.onNode(hasText("Length: 102")).assertIsDisplayed()
    }

    @Test
    fun `test failed state and still show the fact`() = runTest {
        val viewModel = getViewModel(
            expectedSavedFact = BaseResponse.Success(Fact("some fact", 0)),
            expectedUpdateFact = BaseResponse.Failed(code = 404, message = "Not found"),
        )

        composeTestRule.setContent {
            FactScreen(viewModel)
        }
        composeTestRule.onNode(hasText("Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertDoesNotExist()
        composeTestRule.onNode(hasText("Not found")).assertIsDisplayed()
    }
}
