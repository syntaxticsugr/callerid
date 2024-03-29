package com.syntaxticsugr.callerid.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.slaviboy.composeunits.dw

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    prefix: String? = null,
    supportingText: String? = null,
    leadingIcon: ImageVector? = null,
    isError: Boolean = false,
    keyboardType: KeyboardType? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 0.10.dw, top = 0.025.dw, bottom = 0.025.dw),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 0.05.dw)
                    .size(0.08.dw),
            )
        } else {
            Spacer(modifier = Modifier.width((0.05.dw * 2) + 0.08.dw))
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            label = {
                Text(
                    text = label
                )
            },
            prefix = if (prefix != null) {
                {
                    Text(
                        text = prefix
                    )
                }
            } else {
                null
            },
            supportingText = if (supportingText != null) {
                {
                    Text(
                        text = supportingText,
                        fontStyle = FontStyle.Italic,
                    )
                }
            } else {
                null
            },
            isError = isError,
            keyboardOptions = if (keyboardType != null) {
                KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Done
                )
            } else {
                KeyboardOptions.Default
            }
        )
    }
}
