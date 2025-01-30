package jp.speakbuddy.lib_ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import jp.speakbuddy.lib_ui.components.TextBody
import jp.speakbuddy.lib_ui.components.TextBodyBold
import jp.speakbuddy.lib_ui.components.TextTitle
import jp.speakbuddy.lib_ui.components.TextTitleBold
import jp.speakbuddy.lib_ui.helper.ComposableTestExtension
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TextComponentTest : ComposableTestExtension() {

    @Test
    fun `test TextComponent text value`() = runTest {
        composeTestRule.setContent {
            Column {
                TextTitleBold("TextTitleBold")
                TextTitle("TextTitle")
                TextBodyBold("TextBodyBold")
                TextBody("TextBody")
            }
        }

        composeTestRule.onNode(hasText("TextTitleBold")).assertIsDisplayed()
        composeTestRule.onNode(hasText("TextTitle")).assertIsDisplayed()
        composeTestRule.onNode(hasText("TextBodyBold")).assertIsDisplayed()
        composeTestRule.onNode(hasText("TextBody")).assertIsDisplayed()
    }
}
