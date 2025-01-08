package jp.speakbuddy.lib_ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import jp.speakbuddy.lib_ui.components.ButtonText
import jp.speakbuddy.lib_ui.components.IconButtonWithLabel
import jp.speakbuddy.lib_ui.helper.ComposableTestExtension
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ButtonComponentTest : ComposableTestExtension() {

    @Test
    fun `test ButtonComponent`() = runTest {
        var counter = 1
        composeTestRule.setContent {
            ButtonText(
                textButton = "Increase counter"
            ) {
                counter++
            }
        }

        composeTestRule.onNode(hasText("Increase counter")).assertIsDisplayed().performClick()
        assertEquals(2, counter)
    }

    @Test
    fun `test IconButtonWithLabel`() = runTest {
        var counter = 10
        composeTestRule.setContent {
            IconButtonWithLabel(
                icon = Icons.Filled.AddCircle,
                label = "Decrease counter"
            ) {
                counter--
            }
        }

        composeTestRule.onNode(hasText("Decrease counter")).assertIsDisplayed().performClick()
        assertEquals(9, counter)
    }
}
