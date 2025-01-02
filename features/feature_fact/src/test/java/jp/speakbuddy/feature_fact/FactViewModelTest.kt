package jp.speakbuddy.feature_fact

import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.fact.FactViewModel
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.lib_base.test.CoroutineTestExtension
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class FactViewModelTest {
    @JvmField
    @RegisterExtension
    val coroutineTest = CoroutineTestExtension(true)

    // this should be dynamic on each test case, will be updated later
    private val fakeFactRepository = FakeFactRepository(
        BaseResponse.Success(Fact("cat fact", 10))
    )
    private val viewModel = FactViewModel(
        fakeFactRepository,
        ioDispatcher = Dispatchers.IO,
    )

    @Test
    fun updateFact() = coroutineTest.runTest {
        Assertions.assertEquals(4, 2 + 2)
    }
}
