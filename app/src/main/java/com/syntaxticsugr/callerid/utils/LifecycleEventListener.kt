package com.syntaxticsugr.callerid.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun LifecycleEventListener(event: (Lifecycle.Event) -> Unit) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifeCycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            event(event)
        }

        lifeCycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }
}
