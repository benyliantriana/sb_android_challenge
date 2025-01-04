package jp.speakbuddy.feature_fact

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.fact.FactScreen
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.feature_fact.fake.FakeFactViewModel
import jp.speakbuddy.lib_network.response.BaseResponse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FactScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getViewModel(
        expectedSavedFact: BaseResponse<Fact> = BaseResponse.Loading,
        expectedUpdateFact: BaseResponse<Fact> = BaseResponse.Loading,
    ) = FakeFactViewModel(
        FakeFactRepository(expectedSavedFact, expectedUpdateFact),
    )

    @Test
    fun `test empty state FactScreen`() {
        val viewModel = getViewModel(expectedSavedFact = BaseResponse.Loading)

        composeTestRule.setContent {
            FactScreen(viewModel)
        }

        composeTestRule.onNode(hasText("Fact")).assertIsDisplayed()
    }
}
