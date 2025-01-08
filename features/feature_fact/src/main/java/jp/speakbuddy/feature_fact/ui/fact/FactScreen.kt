package jp.speakbuddy.feature_fact.ui.fact

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import jp.speakbuddy.feature_fact.R
import jp.speakbuddy.feature_fact.data.ui.FactUiData
import jp.speakbuddy.feature_fact.util.catImageUrl
import jp.speakbuddy.feature_fact.util.shareFact
import jp.speakbuddy.lib_ui.components.ButtonText
import jp.speakbuddy.lib_ui.components.IconButtonWithLabel
import jp.speakbuddy.lib_ui.components.TextBody
import jp.speakbuddy.lib_ui.components.TextBodyBold
import jp.speakbuddy.lib_ui.components.TextTitle
import jp.speakbuddy.lib_ui.R as RUi

@Composable
fun FactScreen(
    viewModel: FactViewModel = hiltViewModel<FactViewModel>(),
    navigateToFavoriteScreen: () -> Unit,
) {
    val factUiState = viewModel.factUiState.collectAsStateWithLifecycle().value
    val currentFact = viewModel.currentFactResponse.collectAsStateWithLifecycle().value
    val hasMultipleCats = viewModel.hasMultipleCats.collectAsStateWithLifecycle().value
    val isUpdateButtonEnabled = factUiState !is FactUiState.Loading
    val configuration = LocalConfiguration.current

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = lifecycleOwner.lifecycle.currentStateFlow.collectAsState().value

    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            viewModel.getSavedFact()
        }
    }

    Scaffold(
        modifier = Modifier
            .background(colorResource(RUi.color.saffron))
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) { innerPadding ->
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                LandscapeView(
                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                    factUiState = factUiState,
                    hasMultipleCats = hasMultipleCats,
                    currentFact = currentFact,
                    isUpdateButtonEnabled = isUpdateButtonEnabled,
                    updateFact = {
                        viewModel.updateFact()
                    },
                    saveFactToFavorite = {
                        viewModel.saveOrRemoveFactInFavorite(it)
                    },
                    navigateToFavoriteScreen = navigateToFavoriteScreen
                )
            }

            else -> {
                PortraitView(
                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                    factUiState = factUiState,
                    hasMultipleCats = hasMultipleCats,
                    currentFact = currentFact,
                    isUpdateButtonEnabled = isUpdateButtonEnabled,
                    updateFact = {
                        viewModel.updateFact()
                    },
                    saveFactToFavorite = {
                        viewModel.saveOrRemoveFactInFavorite(it)
                    },
                    navigateToFavoriteScreen = navigateToFavoriteScreen
                )
            }
        }
    }
}

@Composable
private fun LandscapeView(
    modifier: Modifier = Modifier,
    factUiState: FactUiState,
    hasMultipleCats: Boolean,
    currentFact: FactUiData,
    isUpdateButtonEnabled: Boolean,
    updateFact: () -> Unit,
    saveFactToFavorite: (FactUiData) -> Unit,
    navigateToFavoriteScreen: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(RUi.color.saffron))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CatImage(modifier = Modifier.weight(1f))
        Spacer(Modifier.width(40.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            FactView(
                factUiState = factUiState,
                hasMultipleCats = hasMultipleCats,
                currentFact = currentFact,
                isUpdateButtonEnabled = isUpdateButtonEnabled,
                updateFact = updateFact,
                saveFactToFavorite = saveFactToFavorite,
                navigateToFavoriteScreen = navigateToFavoriteScreen
            )
        }
    }
}

@Composable
private fun PortraitView(
    modifier: Modifier = Modifier,
    factUiState: FactUiState,
    hasMultipleCats: Boolean,
    currentFact: FactUiData,
    isUpdateButtonEnabled: Boolean,
    updateFact: () -> Unit,
    saveFactToFavorite: (FactUiData) -> Unit,
    navigateToFavoriteScreen: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(RUi.color.saffron))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(100.dp))
        CatImage()
        FactView(
            factUiState = factUiState,
            hasMultipleCats = hasMultipleCats,
            currentFact = currentFact,
            isUpdateButtonEnabled = isUpdateButtonEnabled,
            updateFact = updateFact,
            saveFactToFavorite = saveFactToFavorite,
            navigateToFavoriteScreen = navigateToFavoriteScreen,
        )
    }
}

@Composable
private fun FactView(
    factUiState: FactUiState,
    hasMultipleCats: Boolean,
    currentFact: FactUiData,
    isUpdateButtonEnabled: Boolean,
    updateFact: () -> Unit,
    saveFactToFavorite: (FactUiData) -> Unit,
    navigateToFavoriteScreen: () -> Unit,
) {
    Spacer(Modifier.height(20.dp))
    Title()
    Spacer(Modifier.height(20.dp))
    MultipleCat(hasMultipleCats)
    Fact(factUiState, currentFact)
    FactLength(factUiState, currentFact)
    Spacer(Modifier.height(10.dp))
    FactSaveAndShareButton(factUiState, currentFact, saveFactToFavorite)
    Spacer(Modifier.height(10.dp))
    FactUpdateButton(isUpdateButtonEnabled) {
        updateFact()
    }
    FactError(factUiState)
    FactFavoriteButton {
        navigateToFavoriteScreen()
    }
}

@Composable
private fun Title() {
    TextTitle(stringResource(R.string.fact_title))
}

@Composable
private fun MultipleCat(hasMultipleCats: Boolean) {
    if (hasMultipleCats) {
        TextBodyBold(stringResource(R.string.fact_multiple_cats))
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun Fact(factUiState: FactUiState, currentFact: FactUiData) {
    val factDescription = when (factUiState) {
        is FactUiState.Success -> factUiState.factUiData.fact
        else -> currentFact.fact
    }
    if (factDescription.isNotEmpty()) {
        TextBody(factDescription)
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun FactLength(factUiState: FactUiState, currentFact: FactUiData) {
    val length = when (factUiState) {
        is FactUiState.Success -> factUiState.factUiData.length
        else -> currentFact.fact.length
    }
    if (length > 100) {
        TextBodyBold(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.fact_length, length),
            textAlign = TextAlign.Right
        )
    }
}

@Composable
private fun FactSaveAndShareButton(
    factUiState: FactUiState,
    currentFact: FactUiData,
    saveFactToFavorite: (FactUiData) -> Unit,
) {
    val factData = when (factUiState) {
        is FactUiState.Success -> factUiState.factUiData
        else -> currentFact
    }
    if (factData.fact.isNotEmpty()) {
        val context = LocalContext.current
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f))
            if (factData.isFavorite) {
                IconButtonWithLabel(
                    icon = Icons.Filled.Favorite,
                    label = stringResource(R.string.fact_liked),
                    tintColor = colorResource(RUi.color.light_red)
                ) {
                    saveFactToFavorite(factData)
                }
            } else {
                IconButtonWithLabel(
                    icon = Icons.Filled.FavoriteBorder,
                    label = stringResource(R.string.fact_like)
                ) {
                    saveFactToFavorite(factData)
                }
            }
            Spacer(Modifier.width(8.dp))
            IconButtonWithLabel(
                icon = Icons.Filled.Share,
                label = stringResource(R.string.fact_share)
            ) {
                context.shareFact(factData.fact)
            }
        }
    }
}

@Composable
private fun FactUpdateButton(
    isUpdateButtonEnabled: Boolean,
    onUpdateClick: () -> Unit,
) {
    val updateButtonText = if (isUpdateButtonEnabled) {
        stringResource(R.string.fact_update_fact)
    } else {
        stringResource(RUi.string.loading)
    }
    ButtonText(
        textButton = updateButtonText,
        isEnabled = isUpdateButtonEnabled,
    ) {
        onUpdateClick()
    }
}

@Composable
private fun FactError(factUiState: FactUiState) {
    val textButton = if (factUiState is FactUiState.Failed) {
        factUiState.message
    } else ""
    TextBody(
        text = textButton,
        color = colorResource(RUi.color.light_red),
    )
    if (textButton.isNotEmpty()) {
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun CatImage(
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        modifier = modifier.fillMaxWidth(),
        model = catImageUrl,
        contentDescription = null,
    )
}

@Composable
private fun FactFavoriteButton(
    navigateToFavoriteScreen: () -> Unit,
) {
    ButtonText(stringResource(R.string.fact_favorite_list)) {
        navigateToFavoriteScreen()
    }
}
