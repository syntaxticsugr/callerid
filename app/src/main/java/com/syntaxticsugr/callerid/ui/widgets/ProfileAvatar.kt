package com.syntaxticsugr.callerid.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp

@Composable
fun ProfileAvatar(
    isValidPhoneNumber: Boolean,
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
            ),
            color = if (isValidPhoneNumber) {
                Color.Unspecified
            } else {
                MaterialTheme.colorScheme.error
            }
        )

        if (isValidPhoneNumber && name.isNullOrBlank()) {
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
                color = if (isValidPhoneNumber) {
                    ProgressIndicatorDefaults.circularColor
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }
    }

}
