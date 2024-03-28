package com.syntaxticsugr.callerid.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.ui.widgets.CallerCard
import com.syntaxticsugr.callerid.ui.widgets.SearchBar
import com.syntaxticsugr.callerid.utils.getCallsLog
import com.syntaxticsugr.callerid.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val callsLog = getCallsLog(context = context)
    val dates = callsLog.keys.toList()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { dates.size })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedTabIndex = page
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.04.dw),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(context = context, homeViewModel = homeViewModel)

                IconButton(
                    onClick = {
                        homeViewModel.openProfilePage(navController)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_more_vert_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            }

            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 0.dw
            ) {
                dates.forEachIndexed { index, date ->
                    Tab(
                        text = {
                            Text(
                                text = date
                            )
                        },
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
                val knownCallers = callsLog[selectedDate]!!["known"]!!
                val unknownCallers = callsLog[selectedDate]!!["unknown"]!!
                val knownCallersList = knownCallers.keys.toList()
                val unknownCallersList = unknownCallers.keys.toList()

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 0.04.dw)
                ) {
                    if (unknownCallersList.isNotEmpty()) {
                        item {
                            Text(
                                modifier = Modifier
                                    .padding(top = 0.04.dw),
                                text = "Unknown Callers"
                            )
                        }

                        items(unknownCallersList) { phoneNumber ->
                            CallerCard(
                                context = context,
                                navController = navController,
                                call = unknownCallers[phoneNumber]!!
                            )
                        }
                    }

                    if (knownCallersList.isNotEmpty()) {
                        item {
                            Text(
                                modifier = Modifier
                                    .padding(top = 0.04.dw),
                                text = "From Contacts"
                            )
                        }

                        items(knownCallersList) { phoneNumber ->
                            CallerCard(
                                context = context,
                                navController = navController,
                                call = knownCallers[phoneNumber]!!
                            )
                        }
                    }
                }
            }
        }
    }
}
