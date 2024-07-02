package com.example.whatsappcompose.core.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.whatsappcompose.core.util.UiText

@Composable
fun ShowAlertDialog(
    title: UiText,
    text: UiText,
    positiveText: UiText,
    negativeText: UiText,
    onPositiveButton: () -> Unit,
    onNegativeButton: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = title.asString())
        },
        text = {
            Text(text = text.asString())
        },
        onDismissRequest = {
            onNegativeButton()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPositiveButton()
                }
            ) {
                Text(positiveText.asString())
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onNegativeButton()
                }
            ) {
                Text(negativeText.asString())
            }
        }
    )
}