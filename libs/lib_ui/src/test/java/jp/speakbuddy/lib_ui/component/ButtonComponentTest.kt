package jp.speakbuddy.lib_ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import jp.speakbuddy.lib_base.test.ComposableTestExtension
import jp.speakbuddy.lib_ui.components.DefaultButton
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ButtonComponentTest : ComposableTestExtension() {

    @Test
    fun `test ButtonComponent`() = runTest {
        var counter = 1
        composeTestRule.setContent {
            DefaultButton(
                textButton = "Increase counter"
            ) {
                counter++
            }
        }

        composeTestRule.onNode(hasText("Increase counter")).assertIsDisplayed().performClick()
        assertEquals(2, counter)
    }
}
