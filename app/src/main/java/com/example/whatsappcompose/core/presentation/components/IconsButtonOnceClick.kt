package com.example.whatsappcompose.core.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun IconButtonOnceClick(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    conditional: () -> Boolean = { true },
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    var onClickHasExecuted by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            if (conditional() && !onClickHasExecuted) {
                onClickHasExecuted = true
                onClick()
            }
        },
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
        content = content
    )
}