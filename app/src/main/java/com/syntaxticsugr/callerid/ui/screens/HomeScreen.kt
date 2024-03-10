package com.syntaxticsugr.callerid.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.ui.widgets.CallerCard
import com.syntaxticsugr.callerid.utils.getCallsLog
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val callLogs by remember { mutableStateOf(getCallsLog(context = context)) }
    val dates = callLogs.keys.toList()

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val pagerState = rememberPagerState(pageCount = { dates.size })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedTabIndex = page
        }
    }

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
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { page ->
                val selectedDate = dates[page]
                val knownCallers = callLogs[selectedDate]!!["known"]!!
                val unknownCallers = callLogs[selectedDate]!!["unknown"]!!
                val knownCallersList = knownCallers.keys.toList()
                val unknownCallersList = unknownCallers.keys.toList()

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 0.04.dw)
                ) {
                    if (unknownCallersList.isNotEmpty()) {
                        item {
                            Text(
                                text = "Unknown Callers",
                                modifier = Modifier
                                    .padding(vertical = 0.04.dw),
                            )
                        }

                        items(unknownCallersList) { phoneNumber ->
                            CallerCard(call = unknownCallers[phoneNumber]!!, navController)
                        }
                    }

                    if (knownCallersList.isNotEmpty()) {
                        item {
                            Text(
                                text = "From Contacts",
                                modifier = Modifier
                                    .padding(vertical = 0.04.dw),
                            )
                        }

                        items(knownCallersList) { phoneNumber ->
                            CallerCard(call = knownCallers[phoneNumber]!!, navController)
                        }
                    }
                }
            }
        }
    }
}
