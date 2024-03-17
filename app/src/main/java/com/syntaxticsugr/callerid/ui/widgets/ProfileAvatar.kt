package com.syntaxticsugr.callerid.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp

@Composable
fun ProfileAvatar(
    name: String?,
    size: Dp
) {

    val initial = if (name.isNullOrBlank()) "?" else name.take(1).uppercase()

    Box(
        modifier = Modifier
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )

        if (name.isNullOrBlank()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize(),
                strokeWidth = size / 20,
            )
        } else {
            CircularProgressIndicator(
                progress = { 1.0f },
                modifier = Modifier
                    .fillMaxSize(),
                strokeWidth = size / 20,
            )
        }
    }

}
