package jp.speakbuddy.feature_fact.ui.fact

import jp.speakbuddy.feature_fact.data.response.FactResponse

sealed class FactUiState {
    data object Loading : FactUiState()
    data class Success(val factResponseData: FactResponse) : FactUiState()
    data class Failed(val code: Int, val message: String) : FactUiState()
}
