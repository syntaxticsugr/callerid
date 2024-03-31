package com.syntaxticsugr.callerid.ui.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.utils.PermissionsManager
import com.syntaxticsugr.callerid.viewmodel.PermissionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PermissionsScreen(
    navController: NavHostController,
    permissionViewModel: PermissionsViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var buttonText by remember { mutableStateOf(permissionViewModel.getButtonText(context)) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        buttonText = permissionViewModel.getButtonText(context)
    }

    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            buttonText = permissionViewModel.getButtonText(context)
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 0.10.dw),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("CallerID need the following permissions to work.")
                    }
                    append("\n\nREAD_CALL_LOGS")
                    append("\nTo display call logs within the App.")
                    append("\n\nCALL_PHONE")
                    append("\nTo make calls from within the App.")
                }
            )

            Spacer(modifier = Modifier.height(0.10.dw))

            Button(
                onClick = {
                    when (buttonText) {
                        "Grant" -> permissionLauncher.launch(PermissionsManager.requiredPermissions)
                        "Next" -> permissionViewModel.nextScreen(navController)
                        "Settings" -> permissionViewModel.openAppSettings(context as Activity)
                    }
                }
            ) {
                Text(
                    text = buttonText
                )
            }
        }
    }
}
