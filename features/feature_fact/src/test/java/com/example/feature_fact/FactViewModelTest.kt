package com.example.feature_fact

import com.example.feature_fact.data.Fact
import com.example.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import org.junit.Test

class FactViewModelTest {

    // this should be dynamic on each test case, will be updated later
    private val fakeFactRepository = FakeFactRepository(
        BaseResponse.Success(Fact("cat fact", 10))
    )
    private val viewModel = com.example.feature_fact.fact.FactViewModel(
        fakeFactRepository,
        ioDispatcher = Dispatchers.IO,
    )

    @Test
    fun updateFact() {
    }
}
