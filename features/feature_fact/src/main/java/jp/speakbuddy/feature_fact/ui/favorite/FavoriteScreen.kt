package jp.speakbuddy.feature_fact.ui.favorite

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.speakbuddy.feature_fact.R
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.lib_ui.components.TextBody
import jp.speakbuddy.lib_ui.components.TextTitle
import kotlinx.coroutines.launch
import jp.speakbuddy.lib_ui.R as RUi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel<FavoriteViewModel>(),
    navigateUp: () -> Unit,
) {
    val favoriteUiState = viewModel.favoriteUiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val snackBarText = stringResource(R.string.fact_remove_favorite)
    val undoTextAction = stringResource(R.string.fact_undo)

    fun showRemoveFactSnackBar() {
        scope.launch {
            val result = snackBarHostState.showSnackbar(
                message = snackBarText,
                actionLabel = undoTextAction,
                duration = SnackbarDuration.Short
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    viewModel.undoRemoveFavoriteFact()
                }

                else -> {
                    viewModel.doRemoveFavoriteFact()
                }
            }
        }
    }

    BackHandler {
        snackBarHostState.currentSnackbarData?.dismiss()
        navigateUp()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        content = { _ ->
            Column(
                modifier = Modifier
                    .background(colorResource(RUi.color.saffron))
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(Modifier.height(10.dp))
                BackButton {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    navigateUp()
                }
                Spacer(Modifier.height(20.dp))
                Title()
                Spacer(Modifier.height(20.dp))
                FavoriteList(favoriteUiState) {
                    viewModel.removeTempFavoriteFact(it)
                    showRemoveFactSnackBar()
                }
            }
        }
    )
}

@Composable
private fun BackButton(
    navigateUp: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = navigateUp
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
        )
    }
}

@Composable
private fun Title() {
    TextTitle(
        text = stringResource(R.string.fact_favorite_list),
    )
}

@Composable
private fun FavoriteList(
    favoriteUiState: FavoriteUiState,
    onRemoveFavoriteFact: (Int) -> Unit,
) {
    when (favoriteUiState) {
        is FavoriteUiState.Success -> {
            val state = rememberLazyListState()
            LazyColumn(state = state) {
                itemsIndexed(favoriteUiState.favoriteList) { index, favoriteFact ->
                    FavoriteFact(favoriteFact) {
                        onRemoveFavoriteFact(index)
                    }
                }
            }
        }

        else -> {
            // todo implement loading state and failed (empty) state
        }
    }
}

@Composable
private fun FavoriteFact(
    favoriteFact: FactUiData,
    onRemoveFavoriteFact: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        TextBody(
            text = favoriteFact.fact,
            modifier = Modifier.padding(8.dp)
        )
        TextBody(
            text = stringResource(R.string.fact_remove),
            color = colorResource(RUi.color.light_red),
            textAlign = TextAlign.Right,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        onRemoveFavoriteFact()
                    },
                )
        )
    }
    Spacer(Modifier.height(8.dp))
}
