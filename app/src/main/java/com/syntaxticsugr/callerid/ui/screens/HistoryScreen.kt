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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.datamodel.CallModel
import com.syntaxticsugr.callerid.enums.PermissionsResult
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.ui.widgets.CallerCard
import com.syntaxticsugr.callerid.ui.widgets.CenteredLinearProgressIndicator
import com.syntaxticsugr.callerid.ui.widgets.ProfileAvatar
import com.syntaxticsugr.callerid.utils.CallsLog
import com.syntaxticsugr.callerid.utils.PermissionsManager
import com.syntaxticsugr.callerid.utils.PhoneNumberInfo
import com.syntaxticsugr.callerid.utils.makePhoneCall

@Composable
fun HistoryScreen(
    navController: NavController,
    phoneNumber: String
) {
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var calls by remember { mutableStateOf<List<CallModel>>(emptyList()) }
    var name by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        calls = CallsLog.byPhoneNumber(context, phoneNumber)
        name = calls[0].name

        if (name.isNullOrBlank()) {
            name = PhoneNumberInfo.getName(context = context, phoneNumber = calls[0].phoneNumber)
        }
    }

    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            val arePermissionsGranted = PermissionsManager.arePermissionsGranted(context)

            if (arePermissionsGranted != PermissionsResult.ALL_GRANTED) {
                navController.navigate(Screens.Permissions.route) {
                    popUpTo(Screens.History.route) {
                        inclusive = true
                    }
                }
            } else {
                calls = CallsLog.byPhoneNumber(context, phoneNumber)
            }
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 0.04.dw),
        ) {
            if (calls.isEmpty()) {
                CenteredLinearProgressIndicator()
            } else {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 0.02.dw, vertical = 0.06.dw),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileAvatar(isValidPhoneNumber = true, name = name, size = 0.14.dw)

                    Spacer(modifier = Modifier.width(0.06.dw))

                    Column {
                        if (!name.isNullOrBlank()) {
                            Text(
                                text = name!!,
                                fontSize = 0.06.sw
                            )
                        }
                        Text(
                            text = phoneNumber,
                            fontSize = 0.05.sw
                        )
                    }
                }

                LazyColumn {
                    items(calls) { call ->
                        CallerCard(call = call)
                    }
                }
            }
        }
    }
}
