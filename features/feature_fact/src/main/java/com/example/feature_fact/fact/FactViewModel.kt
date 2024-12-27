package com.example.feature_fact.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_fact.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: com.example.feature_fact.repository.FactRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _fact = MutableStateFlow<com.example.feature_fact.data.Fact?>(null)
    val fact: StateFlow<com.example.feature_fact.data.Fact?> get() = _fact

    init {
        getSavedFact()
    }

    private fun getSavedFact() {
        viewModelScope.launch(ioDispatcher) {
            factRepository.getSavedFact().collect { result ->
                when (result) {
                    is BaseResponse.Success -> {
                        _fact.value = result.data
                    }

                    is BaseResponse.Failed -> {
                        _fact.value = com.example.feature_fact.data.Fact(
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
                    is BaseResponse.Success -> {
                        _fact.value = result.data
                    }

                    is BaseResponse.Failed -> {
                        _fact.value = com.example.feature_fact.data.Fact(
                            fact = result.message,
                            length = -1
                        )
                    }
                }
            }
        }
    }
}
