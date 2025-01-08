package jp.speakbuddy.feature_fact.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.feature_fact.fake.FakeFavoriteViewModel
import jp.speakbuddy.feature_fact.helper.ComposableTestExtension
import jp.speakbuddy.feature_fact.ui.favorite.FavoriteScreen
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FavoriteScreenTest : ComposableTestExtension() {
    private fun getViewModel(
        savedFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
        updateFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
        savedFavoriteListResult: BaseResponse<List<FactUiData>> = BaseResponse.Loading,
    ) = FakeFavoriteViewModel(
        FakeFactRepository(
            savedFactResult,
            updateFactResult,
            savedFavoriteListResult
        ),
    )

    @Test
    fun `test success state get favorite list`() = runTest {
        val viewModel = getViewModel(
            savedFavoriteListResult = BaseResponse.Success(
                listOf(
                    FactUiData("cat1", 4, true),
                    FactUiData("cat21", 5, true)
                )
            ),
        )

        composeTestRule.setContent {
            FavoriteScreen(viewModel) {
                composeTestRule.onNode(hasText("Favorite List")).assertIsDisplayed()
                composeTestRule.onNode(hasText("cat1")).assertIsDisplayed()
                composeTestRule.onNode(hasText("cat21")).assertIsDisplayed()
                composeTestRule.onNode(hasText("remove")).assertIsDisplayed()
            }
        }
    }

    @Test
    fun `test remove fact from favorite list`() = runTest {
        val viewModel = getViewModel(
            savedFavoriteListResult = BaseResponse.Success(
                listOf(
                    FactUiData("cat1", 4, true),
                    FactUiData("cat21", 5, true)
                )
            ),
        )

        composeTestRule.setContent {
            FavoriteScreen(viewModel) {
                composeTestRule.onNode(hasText("Favorite List")).assertIsDisplayed()
                composeTestRule.onNode(hasText("cat1")).assertIsDisplayed()
                composeTestRule.onNode(hasText("cat21")).assertIsDisplayed()
                composeTestRule.onNode(hasText("remove")).assertIsDisplayed().performClick()
                composeTestRule.onNode(hasText("Fact removed from favorite")).assertIsDisplayed()
                composeTestRule.onNode(hasText("cat1")).assertDoesNotExist()
            }
        }
    }

    @Test
    fun `test undo remove fact from favorite list`() = runTest {
        val viewModel = getViewModel(
            savedFavoriteListResult = BaseResponse.Success(
                listOf(
                    FactUiData("cat1", 4, true),
                    FactUiData("cat21", 5, true)
                )
            ),
        )

        composeTestRule.setContent {
            FavoriteScreen(viewModel) {
                composeTestRule.onNode(hasText("Favorite List")).assertIsDisplayed()
                composeTestRule.onNode(hasText("cat1")).assertIsDisplayed()
                composeTestRule.onNode(hasText("cat21")).assertIsDisplayed()
                composeTestRule.onNode(hasText("remove")).assertIsDisplayed().performClick()
                composeTestRule.onNode(hasText("Fact removed from favorite")).assertIsDisplayed()
                composeTestRule.onNode(hasText("Undo")).assertIsDisplayed()
                composeTestRule.onNode(hasText("cat1")).assertIsDisplayed()
            }
        }
    }
}
