package com.syntaxticsugr.callerid.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.utils.PhoneNumberInfo
import org.json.JSONObject

@Composable
fun PhoneNumberInfoDialog(
    phoneNumber: String,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    var info: JSONObject
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var spamReports by remember { mutableStateOf(0) }
    var city by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        LaunchedEffect(phoneNumber) {
            info = PhoneNumberInfo.getInfo(context = context, phoneNumber = phoneNumber)
            val internetAddresses = info.getJSONArray("internetAddresses")
            val address = info.getJSONArray("addresses").getJSONObject(0)

            name = info.getString("name")
            if (internetAddresses.length() >= 1) {
                email = internetAddresses.getJSONObject(0).getString("id")
            }
            if (info.has("spamInfo")) {
                val spamInfo = info.getJSONObject("spamInfo")
                if (spamInfo.has("spamStats")) {
                    spamReports = spamInfo.getJSONObject("spamStats").getInt("numReports")
                }
            }
            city = address.getString("city")
            countryCode = address.getString("countryCode")
        }

        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Spacer(modifier = Modifier.height(0.09.dw))

                Card(
                    Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(0.06.dw)
                ) {
                    Spacer(modifier = Modifier.height(0.09.dw))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 0.02.dw),
                        text = buildAnnotatedString {
                            append(name)
                            append("\n$phoneNumber")
                            if (email.isNotBlank()) {
                                append("\n$email")
                            }
                            if (spamReports != 0) {
                                append("\nSpam Reports: $spamReports")
                            }
                            append("\n\n$city | $countryCode")
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }

            ProfileAvatar(
                isValidPhoneNumber = true,
                name = name,
                size = 0.18.dw,
                backgroundColor = CardDefaults.cardColors().containerColor
            )
        }
    }
}
