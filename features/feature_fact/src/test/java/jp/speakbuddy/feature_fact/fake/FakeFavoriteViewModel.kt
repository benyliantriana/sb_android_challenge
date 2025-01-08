package jp.speakbuddy.feature_fact.fake

import jp.speakbuddy.feature_fact.ui.favorite.FavoriteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class FakeFavoriteViewModel(
    fakeRepository: FakeFactRepository,
    testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : FavoriteViewModel(fakeRepository, testDispatcher)
