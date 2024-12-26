package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.di.IODispatcher
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.lib_datastore.FactPreference
import jp.speakbuddy.lib_datastore.copy
import jp.speakbuddy.network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: FactRepository,
    private val factDataStore: DataStore<FactPreference>,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _fact = MutableStateFlow<String?>(null)
    val fact: StateFlow<String?> get() = _fact

    init {
        getSavedFact()
    }

    fun updateFact() {
        viewModelScope.launch {
            factRepository.getFact().collectLatest { result ->
                when (result) {
                    is BaseResponse.Success -> {
                        _fact.value = result.data.fact
                        saveFact(result.data.fact)
                    }

                    is BaseResponse.Failed -> {
                        _fact.value = result.message
                    }
                }
            }
        }
    }

    private fun saveFact(newFact: String) {
        viewModelScope.launch(ioDispatcher) {
            try {
                factDataStore.updateData {
                    it.copy { this.fact = newFact }
                }
            } catch (ioException: IOException) {
                println("savingFact: " + ioException.message)
            }
        }
    }

    private fun getSavedFact() {
        viewModelScope.launch(ioDispatcher) {
            try {
                factDataStore.data.collect {
                    _fact.value = it.fact
                }
            } catch (ioException: IOException) {
                println("getFact:" + ioException.message)
            }
        }
    }
}
