package com.syntaxticsugr.callerid.ui.screens

import CustomTextField
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.utils.rememberImeState
import com.syntaxticsugr.callerid.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LogInScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = koinViewModel()
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(400))
        } else {
            scrollState.animateScrollTo(0, tween(400))
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState, enabled = false)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(0.25.dh))

            CustomTextField(
                value = loginViewModel.firstName,
                onValueChange = { loginViewModel.firstName = it },
                label = "First Name",
                leadingIcon = Icons.Filled.Person,
                isError = loginViewModel.firstNameError
            )
            CustomTextField(
                value = loginViewModel.lastName,
                onValueChange = { loginViewModel.lastName = it },
                label = "Last Name",
                isError = loginViewModel.lastNameError
            )
            CustomTextField(
                value = loginViewModel.phoneNumber,
                onValueChange = { loginViewModel.phoneNumber = it },
                label = "Phone Number",
                prefix = "+",
                leadingIcon = Icons.Filled.Phone,
                isError = loginViewModel.phoneNumberError,
                keyboardType = KeyboardType.Number
            )
            CustomTextField(
                value = loginViewModel.email,
                onValueChange = { loginViewModel.email = it },
                label = "Email",
                leadingIcon = Icons.Filled.Email,
                supportingText = "optional",
                isError = loginViewModel.emailError,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(0.10.dw))

            Button(
                onClick = {
                    keyboardController?.hide()
                    loginViewModel.nextScreen(navController)
                }
            ) {
                Text("Next")
            }

            Spacer(modifier = Modifier.height(0.40.dh))
        }
    }

}
