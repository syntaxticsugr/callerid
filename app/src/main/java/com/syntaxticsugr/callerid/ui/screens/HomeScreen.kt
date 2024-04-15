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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.datamodel.CallModel
import com.syntaxticsugr.callerid.enums.PermissionsResult
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.ui.widgets.CallerCard
import com.syntaxticsugr.callerid.ui.widgets.CenteredLinearProgressIndicator
import com.syntaxticsugr.callerid.ui.widgets.SearchBar
import com.syntaxticsugr.callerid.utils.CallsLog
import com.syntaxticsugr.callerid.utils.LifecycleEventListener
import com.syntaxticsugr.callerid.utils.PermissionsManager
import com.syntaxticsugr.callerid.utils.navigateAndClean
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

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { homeViewModel.dates.size })

    LifecycleEventListener { event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (PermissionsManager.arePermissionsGranted(context) == PermissionsResult.ALL_GRANTED) {
                    homeViewModel.getDates()
                } else {
                    navController.navigateAndClean(Screens.Permissions.route)
                }
            }
            else -> {}
        }
    }

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
                SearchBar()

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

            if (homeViewModel.dates.isEmpty()) {
                CenteredLinearProgressIndicator()
            } else {
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    edgePadding = 0.dw
                ) {
                    homeViewModel.dates.forEachIndexed { index, date ->
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
                    val selectedDate = homeViewModel.dates[page]

                    var callsLog by remember { mutableStateOf<Map<String, Map<String, CallModel>>>(emptyMap()) }
                    lateinit var knownCallers: Map<String, CallModel>
                    lateinit var unknownCallers: Map<String, CallModel>
                    lateinit var knownCallersList: List<String>
                    lateinit var unknownCallersList: List<String>

                    LifecycleEventListener { event ->
                        when (event) {
                            Lifecycle.Event.ON_RESUME -> {
                                coroutineScope.launch {
                                    callsLog = CallsLog.byDate(context = context, date = selectedDate)
                                }
                            }
                            else -> {}
                        }
                    }

                    if (callsLog.isEmpty()) {
                        CenteredLinearProgressIndicator()
                    } else {
                        knownCallers = callsLog["known"]!!
                        unknownCallers = callsLog["unknown"]!!
                        knownCallersList = knownCallers.keys.toList()
                        unknownCallersList = unknownCallers.keys.toList()

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
    }
}
