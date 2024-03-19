package com.syntaxticsugr.callerid.ui.widgets

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.datamodel.CallModel
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.utils.PhoneNumberInfoHelper
import com.syntaxticsugr.callerid.utils.callTypeString
import com.syntaxticsugr.callerid.utils.isValidPhoneNumber
import com.syntaxticsugr.callerid.utils.makePhoneCall
import com.syntaxticsugr.callerid.utils.savePhoneNumber
import com.syntaxticsugr.callerid.utils.sendMessage

@Composable
fun CallerCard(
    context: Context,
    navController: NavController,
    call: CallModel
) {
    var expanded by remember { mutableStateOf(false) }

    val isValidPhoneNumber by remember { mutableStateOf(isValidPhoneNumber(call.phoneNumber)) }

    var name by remember { mutableStateOf(call.name) }

    if (isValidPhoneNumber && name.isNullOrBlank()) {
        LaunchedEffect(Unit) {
            name = PhoneNumberInfoHelper.getName(context, call.phoneNumber)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.02.dw)
            .clip(RoundedCornerShape(0.04.dw))
            .clickable {
                if (call.name.isNullOrBlank()) {
                    expanded = !expanded
                } else if (isValidPhoneNumber) {
                    navController.navigate("${Screens.History.route}/${call.phoneNumber}")
                }
            }
    ) {
        Row(
            modifier = Modifier
                .padding(0.02.dw),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileAvatar(
                isValidPhoneNumber = isValidPhoneNumber,
                name = name,
                size = 0.12.dw
            )

            Spacer(modifier = Modifier.width(0.04.dw))

            Column {
                if (call.name.isNullOrBlank()) {
                    if (!isValidPhoneNumber) {
                        Text(
                            text = "Invalid Phone Number!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    if (!name.isNullOrBlank()) {
                        Text(text = name!!)
                    }
                    Text(
                        text = call.phoneNumber,
                        color = if (isValidPhoneNumber) {
                            Color.Unspecified
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                } else {
                    Text(text = call.name)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CallTypeIcon(type = call.type, duration = call.duration, size = 0.04.dw)

                    Spacer(modifier = Modifier.width(0.04.dw))

                    Text(text = call.time)

                    if (call.duration != "0 sec") {
                        Spacer(modifier = Modifier.width(0.04.dw))

                        Text(text = call.duration)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isValidPhoneNumber) {
                IconButton(
                    onClick = {
                        makePhoneCall(context, call.phoneNumber)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_call_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            }
        }

        if (isValidPhoneNumber && call.name.isNullOrBlank()) {
            AnimatedVisibility(
                visible = expanded
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.02.dw),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = {
                            sendMessage(context, call.phoneNumber)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_message_24),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate("${Screens.History.route}/${call.phoneNumber}")
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_history_24),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            savePhoneNumber(context, call.phoneNumber, name)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_add_alt_1_24),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CallerCard(
    call: CallModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.04.dw),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CallTypeIcon(type = call.type, duration = call.duration, size = 0.06.dw)

        Spacer(modifier = Modifier.width(0.04.dw))

        Column {
            Text(text = callTypeString(call.type))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = call.date)

                Spacer(modifier = Modifier.width(0.02.dw))

                Text(text = call.time)
            }
        }

        if (call.duration != "0 sec") {
            Spacer(modifier = Modifier.weight(1f))

            Text(text = call.duration)
        }
    }
}
