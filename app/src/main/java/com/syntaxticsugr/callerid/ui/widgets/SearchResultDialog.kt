package com.syntaxticsugr.callerid.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.window.Dialog
import com.slaviboy.composeunits.dw
import org.json.JSONObject

@Composable
fun SearchResultDialog(
    info: JSONObject,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        val name = info.getString("name")
        val phoneNumber = info.getJSONArray("phones").getJSONObject(0).getString("e164Format")
        val address = info.getJSONArray("addresses").getJSONObject(0).getString("address")
        val city = info.getJSONArray("addresses").getJSONObject(0).getString("city")

        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(0.05.dw)
        ) {
            if (name.isBlank()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 0.08.dw, vertical = 0.06.dw),
                    text = "Sorry No Result Found :("
                )
            } else {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 0.08.dw, vertical = 0.06.dw),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileAvatar(isValidPhoneNumber = true, name = name, size = 0.12.dw)

                    Spacer(modifier = Modifier.width(0.04.dw))

                    Column {
                        Text(
                            text = buildAnnotatedString {
                                append(name)
                                append("\n$phoneNumber")
                                append("\n$city $address")
                            }
                        )
                    }
                }
            }
        }
    }
}
