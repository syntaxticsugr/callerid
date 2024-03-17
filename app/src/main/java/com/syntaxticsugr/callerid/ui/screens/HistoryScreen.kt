package com.syntaxticsugr.callerid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.ui.widgets.CallerCard
import com.syntaxticsugr.callerid.ui.widgets.ProfileAvatar
import com.syntaxticsugr.callerid.utils.getCallsLog
import com.syntaxticsugr.callerid.utils.getName
import com.syntaxticsugr.callerid.utils.makePhoneCall

@Composable
fun HistoryScreen(
    phoneNumber: String
) {
    val context = LocalContext.current

    val calls by remember { mutableStateOf(getCallsLog(context, phoneNumber)) }

    var name by remember { mutableStateOf(calls[0].name) }

    if (name.isBlank()) {
        LaunchedEffect(Unit) {
            name = getName(context, calls[0].phoneNumber)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    makePhoneCall(context, phoneNumber)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_call_24),
                    modifier = Modifier.size(0.08.dw),
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 0.04.dw),
        ) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 0.02.dw, vertical = 0.06.dw),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileAvatar(name = name, size = 0.14.dw)

                Spacer(modifier = Modifier.width(0.06.dw))

                Column {
                    Text(
                        text = name,
                        fontSize = 0.06.sw
                    )
                    Text(
                        text = calls[0].phoneNumber,
                        fontSize = 0.05.sw
                    )
                }
            }

            LazyColumn(
            ) {
                items(calls) { call ->
                    CallerCard(call = call)
                }
            }
        }
    }
}
