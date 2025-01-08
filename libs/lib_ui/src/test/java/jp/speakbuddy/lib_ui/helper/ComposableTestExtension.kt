package jp.speakbuddy.lib_ui.helper

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(
    application = TestApplication::class,
    qualifiers = "+w360dp-h640dp-xhdpi", // Small phone, but larger than the default (w320dp-h470dp)
)
@RunWith(AndroidJUnit4::class)
abstract class ComposableTestExtension {
    @get:Rule
    val composeTestRule = createComposeRule()
}
