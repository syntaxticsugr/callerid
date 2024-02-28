package com.syntaxticsugr.callerid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.viewmodel.VerifyViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyScreen(
    navController: NavHostController,
    verifyViewModel: VerifyViewModel = koinViewModel()
) {

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 0.18.dw, end = 0.10.dw),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "${verifyViewModel.firstname} ${verifyViewModel.lastName}"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = verifyViewModel.phoneNumber
            )
            if (verifyViewModel.email.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = verifyViewModel.email
                )
            }

            Spacer(modifier = Modifier.height(0.05.dw))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = verifyViewModel.otp,
                onValueChange = { verifyViewModel.otp = it },
                label = { Text("OTP") },
                isError = verifyViewModel.otpError
            )

            Spacer(modifier = Modifier.height(0.05.dw))

            Button(
                onClick = {
                    verifyViewModel.verifyOTP(navController)
                }
            ) {
                Text("Verify OTP")
            }
        }
    }

}
