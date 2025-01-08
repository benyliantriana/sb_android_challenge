package jp.speakbuddy.feature_fact.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.feature_fact.fake.FakeFactViewModel
import jp.speakbuddy.feature_fact.helper.ComposableTestExtension
import jp.speakbuddy.feature_fact.ui.fact.FactScreen
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FactScreenTest : ComposableTestExtension() {

    private fun getViewModel(
        savedFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
        updateFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
    ) = FakeFactViewModel(
        FakeFactRepository(savedFactResult, updateFactResult),
    )

    @Test
    fun `test loading state FactScreen`() = runTest {
        val viewModel = getViewModel(savedFactResult = BaseResponse.Loading)

        composeTestRule.setContent {
            FactScreen(viewModel) {}
        }

        composeTestRule.onNode(hasText("Cat Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Loading..")).assertIsDisplayed()
    }

    @Test
    fun `test success state no multiple cat`() = runTest {
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Success(FactUiData("some fact", 0, false)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel) {}
        }
        composeTestRule.onNode(hasText("Cat Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Like")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Share")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertDoesNotExist()
    }

    @Test
    fun `test success state has multiple cats`() = runTest {
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Success(FactUiData("some cats", 0, false)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel) {}
        }
        composeTestRule.onNode(hasText("Cat Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some cats")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Like")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Share")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertIsDisplayed()
    }

    @Test
    fun `test success state has length more than 100`() = runTest {
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Success(FactUiData("some cats", 102, false)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel) {}
        }
        println(viewModel.factUiState.value)
        composeTestRule.onNode(hasText("Cat Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some cats")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Like")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Share")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Length: 102")).assertIsDisplayed()
    }

    @Test
    fun `test success state has length below 100`() = runTest {
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Success(FactUiData("some cats", 5, false)),
        )

        composeTestRule.setContent {
            FactScreen(viewModel) {}
        }
        composeTestRule.onNode(hasText("Cat Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some cats")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Like")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Share")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Length: 5")).assertDoesNotExist()
    }

    @Test
    fun `test success state from remote and replace local value`() = runTest {
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Success(FactUiData("some fact", 0, false)),
            updateFactResult = BaseResponse.Success(
                FactUiData(
                    "some another fact",
                    102,
                    false
                )
            ),
        )

        composeTestRule.setContent {
            FactScreen(viewModel) {}
        }
        composeTestRule.onNode(hasText("Cat Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasText("some another fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertDoesNotExist()
        composeTestRule.onNode(hasText("Like")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Share")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Length: 102")).assertIsDisplayed()
    }

    @Test
    fun `test failed state and still show the fact`() = runTest {
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Success(FactUiData("some fact", 0, false)),
            updateFactResult = BaseResponse.Failed(code = 404, message = "Not found"),
        )

        composeTestRule.setContent {
            FactScreen(viewModel) {}
        }
        composeTestRule.onNode(hasText("Cat Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("some fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertDoesNotExist()
        composeTestRule.onNode(hasText("Like")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Share")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Not found")).assertIsDisplayed()
    }

    @Test
    fun `test failed state and not show like and share button`() = runTest {
        val viewModel = getViewModel(
            savedFactResult = BaseResponse.Failed(code = 404, message = "Not found"),
            updateFactResult = BaseResponse.Failed(code = 404, message = "Not found"),
        )

        composeTestRule.setContent {
            FactScreen(viewModel) {}
        }
        composeTestRule.onNode(hasText("Cat Fact")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Update Fact")).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasText("Multiple cats!!!")).assertDoesNotExist()
        composeTestRule.onNode(hasText("Like")).assertDoesNotExist()
        composeTestRule.onNode(hasText("Share")).assertDoesNotExist()
        composeTestRule.onNode(hasText("Not found")).assertIsDisplayed()
    }
}
