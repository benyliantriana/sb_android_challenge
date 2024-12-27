package jp.speakbuddy.edisonandroidexercise.ui

import jp.speakbuddy.edisonandroidexercise.data.Fact
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.ui.fake.FakeFactRepository
import jp.speakbuddy.edisonandroidexercise.ui.fake.testDataStore
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import org.junit.Test

class FactViewModelTest {

    // this should be dynamic on each test case, will be updated later
    private val fakeFactRepository = FakeFactRepository(
        BaseResponse.Success(Fact("cat fact", 10))
    )
    private val viewModel = FactViewModel(
        fakeFactRepository,
        ioDispatcher = Dispatchers.IO,
    )

    @Test
    fun updateFact() {
    }
}
