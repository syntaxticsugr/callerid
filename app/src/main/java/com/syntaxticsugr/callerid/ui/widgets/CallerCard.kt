package com.syntaxticsugr.callerid.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.datamodel.CallerModel

@Composable
fun CallerCard(call: CallerModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.04.dw, vertical = 0.03.dw),
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

                if (call.duration != "00 sec") {
                    Text(text = call.duration)
                }
            }
        }
    }
}
