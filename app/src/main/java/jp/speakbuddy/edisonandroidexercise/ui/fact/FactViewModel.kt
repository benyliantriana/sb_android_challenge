package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: FactRepository,

    // will be used later for data store
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private var _fact = MutableStateFlow<String?>(null)
    val fact: StateFlow<String?> get() = _fact

    fun updateFact() {
        viewModelScope.launch {
            factRepository.getFact().collect { result ->
                when (result) {
                    is BaseResponse.Success -> {
                        _fact.value = result.data.fact
                    }

                    is BaseResponse.Failed -> {
                        _fact.value = result.message
                    }
                }
            }
        }
    }
}
