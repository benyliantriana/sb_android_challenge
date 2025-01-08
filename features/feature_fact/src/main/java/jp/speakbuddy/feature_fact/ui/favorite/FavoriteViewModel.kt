package jp.speakbuddy.feature_fact.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
open class FavoriteViewModel @Inject constructor(
    private val factRepository: FactRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _favoriteUiState = MutableStateFlow<FavoriteUiState>(
        FavoriteUiState.Loading
    )
    val favoriteUiState: StateFlow<FavoriteUiState> get() = _favoriteUiState
    private var currentFavorite: List<FactUiData> = emptyList()
    private var indexFavoriteData: Int? = null

    init {
        getFavoriteFactList()
    }

    private fun getFavoriteFactList() {
        viewModelScope.launch(ioDispatcher) {
            factRepository.getSavedFavoriteFactList().collect { result ->
                when (result) {
                    is BaseResponse.Loading -> _favoriteUiState.value = FavoriteUiState.Loading
                    is BaseResponse.Success -> _favoriteUiState.value = FavoriteUiState.Success(
                        result.data
                    )

                    is BaseResponse.Failed -> _favoriteUiState.value = FavoriteUiState.Failed(
                        result.code, result.message
                    )
                }
            }
        }
    }

    fun removeTempFavoriteFact(index: Int) {
        viewModelScope.launch(ioDispatcher) {
            val currentFavoriteList = (_favoriteUiState.value as FavoriteUiState.Success)
                .favoriteList.toMutableList()

            currentFavorite = currentFavoriteList.toList()

            indexFavoriteData = index

            currentFavoriteList.removeAt(index)
            _favoriteUiState.value = FavoriteUiState.Success(currentFavoriteList)
        }
    }

    fun undoRemoveFavoriteFact() {
        viewModelScope.launch(ioDispatcher) {
            _favoriteUiState.value = FavoriteUiState.Success(currentFavorite)
        }
    }

    fun doRemoveFavoriteFact() {
        viewModelScope.launch(ioDispatcher) {
            indexFavoriteData?.let {
                factRepository.saveFactToFavoriteDataStore(
                    currentFavorite[it].copy(isFavorite = false)
                )
            }
        }
    }
}
