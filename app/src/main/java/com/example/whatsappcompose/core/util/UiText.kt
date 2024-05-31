package com.example.whatsappcompose.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is StringResource -> stringResource(id = resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is StringResource -> context.getString(resId, *args)
        }
    }
}