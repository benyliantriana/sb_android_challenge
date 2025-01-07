package jp.speakbuddy.feature_fact.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.feature_fact.data.response.FactResponse
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.repository.FactRepository
import jp.speakbuddy.lib_base.di.IODispatcher
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FactViewModel @Inject constructor(
    private val factRepository: FactRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _factUiState = MutableStateFlow<FactUiState>(FactUiState.Loading)
    val factUiState: StateFlow<FactUiState> get() = _factUiState

    // for loading state, because loading state don't have any data
    private var _currentFactResponse = MutableStateFlow(FactResponse("", 0))
    val currentFactResponse: StateFlow<FactResponse> get() = _currentFactResponse

    private var _hasMultipleCats = MutableStateFlow(false)
    val hasMultipleCats: StateFlow<Boolean> get() = _hasMultipleCats

    init {
        getSavedFact()
    }

    private fun getSavedFact() {
        viewModelScope.launch(ioDispatcher) {
            factRepository.getSavedFact().collect { result ->
                when (result) {
                    is BaseResponse.Loading -> {
                        _factUiState.value = FactUiState.Loading
                    }

                    is BaseResponse.Success -> {
                        _factUiState.value = FactUiState.Success(result.data)
                        _currentFactResponse.value = currentFactResponse.value.copy(
                            fact = result.data.fact
                        )
                        _hasMultipleCats.value = hasMultipleCats(result.data.fact)
                    }

                    is BaseResponse.Failed -> {
                        _factUiState.value = FactUiState.Failed(result.code, result.message)
                    }
                }
            }
        }
    }

    fun updateFact() {
        viewModelScope.launch(ioDispatcher) {
            factRepository.updateFact().collect { result ->
                when (result) {
                    is BaseResponse.Loading -> {
                        _factUiState.value = FactUiState.Loading
                    }

                    is BaseResponse.Success -> {
                        _factUiState.value = FactUiState.Success(result.data)
                        _currentFactResponse.value = currentFactResponse.value.copy(
                            fact = result.data.fact
                        )
                        _hasMultipleCats.value = hasMultipleCats(result.data.fact)
                    }

                    is BaseResponse.Failed -> {
                        _factUiState.value = FactUiState.Failed(result.code, result.message)
                    }
                }
            }
        }
    }

    fun saveFactToFavorite(factResponse: FactResponse) {
        viewModelScope.launch(ioDispatcher) {
            factRepository.saveFactToFavoriteDataStore(
                FactUiData(
                    factResponse.fact,
                    factResponse.length,
                    true
                )
            )
        }
    }

    private fun hasMultipleCats(fact: String): Boolean {
        return fact.contains("cats", true)
    }
}
