package jp.speakbuddy.feature_fact.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.example.feature_fact.R
import jp.speakbuddy.lib_base.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.lib_base.R as RBase

@Composable
fun FactScreen(
    viewModel: FactViewModel = hiltViewModel<FactViewModel>(),
) {
    val factUiState = viewModel.factUiState.collectAsStateWithLifecycle().value
    val currentFact = viewModel.currentFact.collectAsStateWithLifecycle().value
    val hasMultipleCats = viewModel.hasMultipleCats.collectAsStateWithLifecycle().value
    val isUpdateButtonEnabled = factUiState !is FactUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        FactTitle()
        FactMultipleCatDescription(hasMultipleCats)
        FactDescription(factUiState, currentFact)
        FactLengthDescription(factUiState, currentFact)
        FactUpdateButton(isUpdateButtonEnabled) {
            viewModel.updateFact()
        }
        FactErrorDescription(factUiState)
        CatImage()
    }
}

@Composable
private fun FactTitle() {
    Text(
        text = stringResource(R.string.fact_title),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun FactMultipleCatDescription(hasMultipleCats: Boolean) {
    if (hasMultipleCats) {
        Text(
            text = stringResource(R.string.fact_multiple_cats),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun FactDescription(factUiState: FactUiState, currentFact: String) {
    val factDescription = when (factUiState) {
        is FactUiState.Success -> factUiState.factData.fact
        else -> currentFact
    }
    Text(
        text = factDescription,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun FactLengthDescription(factUiState: FactUiState, currentFact: String) {
    val length = when (factUiState) {
        is FactUiState.Success -> factUiState.factData.length
        else -> currentFact.length
    }
    if (length > 100) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.fact_length, length),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Right
        )
    }
}

@Composable
private fun FactUpdateButton(
    isUpdateButtonEnabled: Boolean,
    onUpdateClick: () -> Unit,
) {
    Button(
        enabled = isUpdateButtonEnabled,
        onClick = onUpdateClick
    ) {
        val updateButtonText = if (isUpdateButtonEnabled) {
            stringResource(R.string.fact_update_fact)
        } else {
            stringResource(RBase.string.loading)
        }
        Text(updateButtonText)
    }
}

@Composable
private fun FactErrorDescription(factUiState: FactUiState) {
    val textButton = if (factUiState is FactUiState.Failed) {
        factUiState.message
    } else ""
    Text(
        text = textButton,
        color = Color.Red,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun CatImage() {
    SubcomposeAsyncImage(
        modifier = Modifier.fillMaxWidth(),
        model = "https://www.pngplay.com/wp-content/uploads/12/Sad-Cat-Meme-Transparent-Free-PNG.png",
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = null,
    )
}

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen()
    }
}
