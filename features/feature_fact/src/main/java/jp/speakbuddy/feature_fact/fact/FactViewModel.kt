package jp.speakbuddy.feature_fact.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.feature_fact.data.Fact
import jp.speakbuddy.feature_fact.repository.FactRepository
import jp.speakbuddy.lib_base.di.IODispatcher
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: FactRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _fact = MutableStateFlow<Fact?>(null)
    val fact: StateFlow<Fact?> get() = _fact

    init {
        getSavedFact()
    }

    private fun getSavedFact() {
        viewModelScope.launch(ioDispatcher) {
            factRepository.getSavedFact().collect { result ->
                when (result) {
                    BaseResponse.Loading -> {
                        _fact.value = Fact(
                            fact = "Loading...",
                            length = -1
                        )
                    }

                    is BaseResponse.Success -> {
                        _fact.value = result.data
                    }

                    is BaseResponse.Failed -> {
                        _fact.value = Fact(
                            fact = result.message,
                            length = -1
                        )
                    }
                }
            }
        }
    }

    fun updateFact() {
        viewModelScope.launch(ioDispatcher) {
            factRepository.updateFact().collectLatest { result ->
                when (result) {
                    is BaseResponse.Loading -> {
                        _fact.value = Fact(
                            fact = "Loading...",
                            length = -1
                        )
                    }

                    is BaseResponse.Success -> {
                        _fact.value = result.data
                    }

                    is BaseResponse.Failed -> {
                        _fact.value = Fact(
                            fact = result.message,
                            length = -1
                        )
                    }
                }
            }
        }
    }
}
