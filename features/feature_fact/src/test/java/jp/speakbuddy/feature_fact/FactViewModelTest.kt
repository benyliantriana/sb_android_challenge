package jp.speakbuddy.feature_fact

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.feature_fact.fact.FactViewModel
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
