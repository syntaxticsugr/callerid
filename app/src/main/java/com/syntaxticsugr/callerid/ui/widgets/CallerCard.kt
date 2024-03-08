package com.syntaxticsugr.callerid.ui.widgets

import android.provider.CallLog
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.datamodel.CallerModel
import com.syntaxticsugr.callerid.navigation.Screens

@Composable
fun CallerCard(call: CallerModel, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.04.dw))
            .clickable {
                if (call.callerName.isNotBlank()) {
                    navController.navigate("${Screens.History.route}/${call.phoneNumber}")
                } else {
                    expanded = !expanded
                }
            }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 0.02.dw, vertical = 0.03.dw),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileAvatar(
                name = call.callerName,
                size = 0.12.dw
            )

            Spacer(modifier = Modifier.width(0.04.dw))

            Column {
                if (call.callerName.isBlank()) {
                    Text(
                        text = call.phoneNumber
                    )
                } else {
                    Text(
                        text = call.callerName
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CallTypeIcon(type = call.type, duration = call.duration, size = 0.04.dw)

                    Spacer(modifier = Modifier.width(0.02.dw))

                    Text(text = call.time)

                    Spacer(modifier = Modifier.width(0.02.dw))

                    if (call.duration != "0 sec") {
                        Text(text = call.duration)
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = expanded
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.03.dw),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("${Screens.History.route}/${call.phoneNumber}")
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_history_24),
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_block_24),
                        tint = if (call.type == CallLog.Calls.BLOCKED_TYPE) {
                            MaterialTheme.colorScheme.error
                        } else {
                            LocalContentColor.current
                        },
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_add_alt_1_24),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
