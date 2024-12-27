package com.example.feature_fact.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FactScreen(
    viewModel: FactViewModel = hiltViewModel<FactViewModel>(),
) {
    val fact = viewModel.fact.collectAsStateWithLifecycle().value
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
        Text(
            text = "Fact",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = fact?.fact.orEmpty(),
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = {
                viewModel.updateFact()
            }
        ) {
            Text(text = "Update fact")
        }
    }
}

//@Preview
//@Composable
//private fun FactScreenPreview() {
//    EdisonAndroidExerciseTheme {
//        FactScreen()
//    }
//}
