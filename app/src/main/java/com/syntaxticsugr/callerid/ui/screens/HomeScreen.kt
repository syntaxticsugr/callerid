package com.syntaxticsugr.callerid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.ui.widgets.CallerCard
import com.syntaxticsugr.callerid.utils.getCallsLog

@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val callLogs by remember { mutableStateOf(getCallsLog(context = context)) }
    val dates = callLogs.keys.toList()

    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 0.dw
            ) {
                dates.forEachIndexed { index, date ->
                    Tab(
                        text = { Text(date) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }

            val selectedDate = dates[selectedTabIndex]
            val selectedCallList = callLogs[selectedDate]!!

            LazyColumn {
                items(selectedCallList) { call ->
                    CallerCard(call = call)
                }
            }
        }
    }
}
