package jp.speakbuddy.feature_fact.fact

import jp.speakbuddy.feature_fact.data.Fact

sealed class FactUiState {
    data object Loading : FactUiState()
    data class Success(val factData: Fact) : FactUiState()
    data class Failed(val code: Int, val message: String) : FactUiState()
}
