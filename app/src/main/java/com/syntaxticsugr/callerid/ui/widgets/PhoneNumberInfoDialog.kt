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

    var info: JSONObject?
    var name by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf<String?>(null) }
    var city by remember { mutableStateOf<String?>(null) }
    var countryCode by remember { mutableStateOf<String?>(null) }
    var spamReports by remember { mutableStateOf<Int?>(null) }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        LaunchedEffect(phoneNumber) {
            info = PhoneNumberInfo.getInfo(context = context, phoneNumber = phoneNumber)

            val internetAddresses = info?.optJSONArray("internetAddresses")?.optJSONObject(0)
            val address = info?.optJSONArray("addresses")?.optJSONObject(0)
            val spamInfo = info?.optJSONObject("spamInfo")?.optJSONObject("spamStats")

            name = info?.optString("name")
            email = internetAddresses?.optString("id")
            city = address?.optString("city")
            countryCode = address?.optString("countryCode")
            spamReports = spamInfo?.optInt("numReports")
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
                            if (!name.isNullOrBlank()) {
                                append(name)
                            }
                            append("\n$phoneNumber")
                            if (!email.isNullOrBlank()) {
                                append("\n$email")
                            }
                            if (spamReports != null && spamReports != 0) {
                                append("\nSpam Reports: $spamReports")
                            }
                            if (!city.isNullOrBlank()) {
                                append("\n\n$city | $countryCode")
                            }
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
