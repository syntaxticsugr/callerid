package com.syntaxticsugr.callerid.utils

import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.syntaxticsugr.callerid.enums.ImeState

@Composable
fun ImeStateListener(state: (ImeState) -> Unit) {
    val view = LocalView.current

    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true

            val imeState = if (isKeyboardOpen) ImeState.VISIBLE else ImeState.HIDDEN

            state(imeState)
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}
