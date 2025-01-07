package jp.speakbuddy.feature_fact.ui.fact

import jp.speakbuddy.feature_fact.data.ui.FactUiData

sealed class FactUiState {
    data object Loading : FactUiState()
    data class Success(val factUiData: FactUiData) : FactUiState()
    data class Failed(val code: Int, val message: String) : FactUiState()
}
