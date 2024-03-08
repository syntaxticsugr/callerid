package com.syntaxticsugr.callerid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.viewmodel.VerifyViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyScreen(
    navController: NavHostController,
    verifyViewModel: VerifyViewModel = koinViewModel()
) {

    val keyboardController = LocalSoftwareKeyboardController.current

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

            if (verifyViewModel.isVerificationSuccessful) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Verification Successful :)"
                )

                Spacer(modifier = Modifier.height(0.05.dw))

                Button(
                    onClick = {
                        verifyViewModel.nextScreen(navController)
                    }
                ) {
                    Text("Next")
                }
            } else {
                if (verifyViewModel.isOtpSent) {
                    if (verifyViewModel.isVerifying) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                        Spacer(modifier = Modifier.height(0.01.dw))

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Verifying OTP"
                        )
                    } else {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = verifyViewModel.otp,
                            onValueChange = { verifyViewModel.otp = it },
                            label = { Text("OTP") },
                            isError = verifyViewModel.otpError
                        )

                        Spacer(modifier = Modifier.height(0.10.dw))

                        Button(
                            onClick = {
                                keyboardController?.hide()
                                verifyViewModel.verifyOTP()
                            }
                        ) {
                            Text("Verify OTP")
                        }
                    }
                } else {
                    if (verifyViewModel.unexpectedError) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = verifyViewModel.getErrorMessage()
                        )
                    } else {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                        Spacer(modifier = Modifier.height(0.01.dw))

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Requesting OTP"
                        )
                    }
                }
            }
        }
    }

}
