package jp.speakbuddy.feature_fact.viewmodel

import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.fake.FakeFactRepository
import jp.speakbuddy.feature_fact.helper.CoroutineTestExtension
import jp.speakbuddy.feature_fact.ui.favorite.FavoriteUiState
import jp.speakbuddy.feature_fact.ui.favorite.FavoriteViewModel
import jp.speakbuddy.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteViewModel {
    @JvmField
    val coroutineTest = CoroutineTestExtension(true)

    private fun getViewModel(
        savedFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
        updateFactResult: BaseResponse<FactUiData> = BaseResponse.Loading,
        savedFavoriteListResult: BaseResponse<List<FactUiData>> = BaseResponse.Loading,
    ) = FavoriteViewModel(
        FakeFactRepository(
            savedFactResult,
            updateFactResult,
            savedFavoriteListResult
        ),
        ioDispatcher = coroutineTest.dispatcher,
    )

    @Test
    fun `init loading state for getFavoriteFactList`() = coroutineTest.runTest {
        // given
        val expected = FavoriteUiState.Loading
        val viewModel = getViewModel(
            savedFavoriteListResult = BaseResponse.Loading
        )

        // when
        val favoriteUiState = viewModel.favoriteUiState.first()

        // then
        assertEquals(expected, favoriteUiState)
    }

    @Test
    fun `init success state for getFavoriteFactList`() = coroutineTest.runTest {
        // given
        val factUiData = FactUiData("cats", 4, true)
        val expected = FavoriteUiState.Success(listOf(factUiData))
        val viewModel = getViewModel(
            savedFavoriteListResult = BaseResponse.Success(listOf(factUiData))
        )

        // when
        val favoriteUiState = viewModel.favoriteUiState.first()

        // then
        assertEquals(expected, favoriteUiState)
    }

    @Test
    fun `remove favorite from local storage`() = coroutineTest.runTest {
        // given
        val factUiData = FactUiData("cats", 4, true)
        val expected = FavoriteUiState.Success(emptyList())
        val viewModel = getViewModel(
            savedFavoriteListResult = BaseResponse.Success(listOf(factUiData))
        )

        // when
        viewModel.removeTempFavoriteFact(0)
        val favoriteUiState = viewModel.favoriteUiState.first()

        // then
        assertEquals(expected, favoriteUiState)
    }

    @Test
    fun `undo remove favorite from local storage`() = coroutineTest.runTest {
        // given
        val factUiData = FactUiData("cats", 4, true)
        val expected = FavoriteUiState.Success(listOf(factUiData))
        val viewModel = getViewModel(
            savedFavoriteListResult = BaseResponse.Success(listOf(factUiData))
        )

        // when
        viewModel.removeTempFavoriteFact(0)
        viewModel.undoRemoveFavoriteFact()
        val favoriteUiState = viewModel.favoriteUiState.first()

        // then
        assertEquals(expected, favoriteUiState)
    }

    @Test
    fun `do remove favorite from local storage`() = coroutineTest.runTest {
        // given
        val factUiData = FactUiData("cats", 4, true)
        val expected = FavoriteUiState.Success(emptyList())
        val viewModel = getViewModel(
            savedFavoriteListResult = BaseResponse.Success(listOf(factUiData))
        )

        // when
        viewModel.removeTempFavoriteFact(0)
        viewModel.doRemoveFavoriteFact()
        val favoriteUiState = viewModel.favoriteUiState.first()

        // then
        assertEquals(expected, favoriteUiState)
    }
}
