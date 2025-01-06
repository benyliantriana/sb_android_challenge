package jp.speakbuddy.lib_base.test

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
open class ComposableTestExtension {
    @get:Rule
    val composeTestRule = createComposeRule()
}
