package jp.speakbuddy.lib_base.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

/**
 * Unit test revert back using Junit4 because robolectric still doesn't support Junit5
 */
@OptIn(ExperimentalCoroutinesApi::class)
open class CoroutineTestExtension(unconfined: Boolean = false) {
    private val scope: TestScope = TestScope()
    val dispatcher: TestDispatcher =
        if (unconfined) UnconfinedTestDispatcher() else StandardTestDispatcher()

    @Before
    fun beforeEach() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun afterEach() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    fun runTest(testBody: suspend TestScope.() -> Unit) = scope.runTest(testBody = testBody)
}
