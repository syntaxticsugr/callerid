import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import com.slaviboy.composeunits.dw

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    prefix: String? = null,
    leadingIcon: ImageVector? = null,
    supportingText: String? = null,
    isError: Boolean = false
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
            Spacer(modifier = Modifier.width((0.05.dw*2) + 0.08.dw))
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            prefix = if (prefix != null) {
                {
                    Text(
                        prefix,
                    )
                }
            } else {
                null
            },
            supportingText = if (supportingText != null) {
                {
                    Text(
                        supportingText,
                        fontStyle = FontStyle.Italic,
                    )
                }
            } else {
                null
            },
            singleLine = true,
            isError = isError,
        )
    }

}
