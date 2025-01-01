package jp.speakbuddy.lib_base.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import jp.speakbuddy.lib_base.R

@Composable
fun DefaultButton(
    textButton: String,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        colors = ButtonDefaults.buttonColors().copy(
            contentColor = colorResource(R.color.blue_sky)
        ),
        enabled = isEnabled,
        onClick = onClick,
        modifier = modifier,
    ) {
        TextBody(
            color = colorResource(R.color.white),
            text = textButton
        )
    }
}