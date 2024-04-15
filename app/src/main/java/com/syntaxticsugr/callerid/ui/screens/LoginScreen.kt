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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.enums.ImeState
import com.syntaxticsugr.callerid.ui.widgets.CustomTextField
import com.syntaxticsugr.callerid.utils.ImeStateListener
import com.syntaxticsugr.callerid.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LogInScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val buttonText = loginViewModel.getButtonText()

    val lockTextField = loginViewModel.isRequestingOtp || loginViewModel.isVerifying || loginViewModel.isVerificationSuccessful

    val focusManager = LocalFocusManager.current
    ImeStateListener { state ->
        when (state) {
            ImeState.HIDDEN -> focusManager.clearFocus()
            else -> {}
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
                modifier = Modifier
                    .fillMaxWidth(),
                text = "LogIn with your Phone Number.",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(0.025.dh))

            CustomTextField(
                value = loginViewModel.phoneNumber,
                onValueChange = { value ->
                    loginViewModel.phoneNumber = value
                    loginViewModel.phoneNumberError = false
                    loginViewModel.otp = ""
                    loginViewModel.otpError = false
                    loginViewModel.isRequestingOtp = false
                    loginViewModel.isOtpSent = false
                    loginViewModel.isVerifying = false
                    loginViewModel.isVerificationSuccessful = false
                    loginViewModel.unexpectedError = false
                },
                readOnly = lockTextField,
                label = "Phone Number",
                prefix = "+",
                isError = loginViewModel.phoneNumberError,
                keyboardType = KeyboardType.Number
            )

            if (loginViewModel.isRequestingOtp) {
                Spacer(modifier = Modifier.height(0.05.dw))

                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(0.02.dw))

                Text(
                    text = "Requesting OTP"
                )
            } else if (loginViewModel.isOtpSent) {
                CustomTextField(
                    value = loginViewModel.otp,
                    onValueChange = { value ->
                        loginViewModel.otp = value
                        loginViewModel.otpError = false
                    },
                    readOnly = lockTextField,
                    label = "OTP",
                    isError = loginViewModel.otpError,
                    keyboardType = KeyboardType.Number
                )
            } else if (loginViewModel.isVerifying) {
                Spacer(modifier = Modifier.height(0.05.dw))

                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(0.01.dw))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Verifying OTP"
                )
            } else if (loginViewModel.isVerificationSuccessful) {
                Spacer(modifier = Modifier.height(0.05.dw))

                Text(
                    text = "Verification Successful :)"
                )
            } else if (loginViewModel.unexpectedError) {
                Spacer(modifier = Modifier.height(0.05.dw))

                Text(
                    text = loginViewModel.errorMessage
                )
            }

            Spacer(modifier = Modifier.height(0.05.dw))

            if (!loginViewModel.isRequestingOtp && !loginViewModel.isVerifying && !loginViewModel.unexpectedError) {
                Button(
                    onClick = {
                        keyboardController?.hide()

                        when (buttonText) {
                            "Next" -> loginViewModel.nextScreen(navController)
                            "Request OTP" -> loginViewModel.validatePhoneAndRequestOtp()
                            "Verify OTP" -> loginViewModel.verifyOtp()
                        }
                    }
                ) {
                    Text(
                        buttonText
                    )
                }
            }
        }
    }
}
