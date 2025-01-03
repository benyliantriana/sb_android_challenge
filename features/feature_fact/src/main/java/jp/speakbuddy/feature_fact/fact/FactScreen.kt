package jp.speakbuddy.feature_fact.fact

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.feature_fact.R
import jp.speakbuddy.feature_fact.util.catImageUrl
import jp.speakbuddy.lib_base.ui.components.DefaultButton
import jp.speakbuddy.lib_base.ui.components.TextBody
import jp.speakbuddy.lib_base.ui.components.TextBodyBold
import jp.speakbuddy.lib_base.ui.components.TextTitle
import jp.speakbuddy.lib_base.R as RBase

@Composable
fun FactScreen(
    viewModel: FactViewModel = hiltViewModel<FactViewModel>(),
) {
    val factUiState = viewModel.factUiState.collectAsStateWithLifecycle().value
    val currentFact = viewModel.currentFact.collectAsStateWithLifecycle().value
    val hasMultipleCats = viewModel.hasMultipleCats.collectAsStateWithLifecycle().value
    val isUpdateButtonEnabled = factUiState !is FactUiState.Loading
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeView(
                factUiState = factUiState,
                hasMultipleCats = hasMultipleCats,
                currentFact = currentFact,
                isUpdateButtonEnabled = isUpdateButtonEnabled,
            ) {
                viewModel.updateFact()
            }
        }

        else -> {
            PortraitView(
                factUiState = factUiState,
                hasMultipleCats = hasMultipleCats,
                currentFact = currentFact,
                isUpdateButtonEnabled = isUpdateButtonEnabled,
            ) {
                viewModel.updateFact()
            }
        }
    }
}

@Composable
private fun LandscapeView(
    factUiState: FactUiState,
    hasMultipleCats: Boolean,
    currentFact: String,
    isUpdateButtonEnabled: Boolean,
    updateFact: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(RBase.color.saffron))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CatImage(
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(40.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(Modifier.height(20.dp))
            Title()
            Spacer(Modifier.height(20.dp))
            MultipleCat(hasMultipleCats)
            Fact(factUiState, currentFact)
            Spacer(Modifier.height(10.dp))
            FactLength(factUiState, currentFact)
            Spacer(Modifier.height(10.dp))
            FactUpdateButton(isUpdateButtonEnabled) {
                updateFact()
            }
            Spacer(Modifier.height(10.dp))
            FactError(factUiState)
        }
    }
}

@Composable
private fun PortraitView(
    factUiState: FactUiState,
    hasMultipleCats: Boolean,
    currentFact: String,
    isUpdateButtonEnabled: Boolean,
    updateFact: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(RBase.color.saffron))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(100.dp))
        CatImage()
        Spacer(Modifier.height(20.dp))
        Title()
        Spacer(Modifier.height(20.dp))
        MultipleCat(hasMultipleCats)
        Fact(factUiState, currentFact)
        Spacer(Modifier.height(10.dp))
        FactLength(factUiState, currentFact)
        Spacer(Modifier.height(10.dp))
        FactUpdateButton(isUpdateButtonEnabled) {
            updateFact()
        }
        Spacer(Modifier.height(10.dp))
        FactError(factUiState)
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
private fun Fact(factUiState: FactUiState, currentFact: String) {
    val factDescription = when (factUiState) {
        is FactUiState.Success -> factUiState.factData.fact
        else -> currentFact
    }
    TextBody(factDescription)
}

@Composable
private fun FactLength(factUiState: FactUiState, currentFact: String) {
    val length = when (factUiState) {
        is FactUiState.Success -> factUiState.factData.length
        else -> currentFact.length
    }
    if (length > 100) {
        TextBodyBold(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.fact_length, length),
            textAlign = TextAlign.Right
        )
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
        stringResource(RBase.string.loading)
    }
    DefaultButton(
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
    Text(
        text = textButton,
        color = colorResource(RBase.color.light_red),
        style = MaterialTheme.typography.bodyLarge
    )
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
