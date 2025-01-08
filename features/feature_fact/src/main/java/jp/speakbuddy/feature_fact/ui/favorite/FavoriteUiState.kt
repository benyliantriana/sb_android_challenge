package jp.speakbuddy.feature_fact.ui.favorite

import jp.speakbuddy.feature_fact.data.ui.FactUiData

sealed class FavoriteUiState {
    data object Loading : FavoriteUiState()
    data class Success(val favoriteList: List<FactUiData>) : FavoriteUiState()
    data class Failed(val code: Int, val message: String) : FavoriteUiState()
}
