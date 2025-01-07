package jp.speakbuddy.lib_ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.speakbuddy.lib_ui.R

@Composable
fun ButtonText(
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

@Composable
fun IconButtonWithLabel(
    icon: ImageVector,
    label: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tintColor: Color = LocalContentColor.current,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tintColor
        )
        Spacer(Modifier.width(4.dp))
        TextBody(label)
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultButtonPreview() {
    Column {
        ButtonText(
            textButton = "Default Button"
        ) {}
        IconButtonWithLabel(
            icon = Icons.Filled.FavoriteBorder,
            label = "Like"
        ) {}
    }
}
