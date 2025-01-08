package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.ui.fact.FactViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class FakeFactViewModel(
    fakeRepository: FakeFactRepository,
    testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : FactViewModel(fakeRepository, testDispatcher)
