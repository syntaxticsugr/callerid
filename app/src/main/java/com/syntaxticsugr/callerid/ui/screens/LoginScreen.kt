package com.syntaxticsugr.callerid.ui.screens

import LoginTextField
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.utils.rememberImeState
import com.syntaxticsugr.callerid.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(0.50.dw))

            LoginTextField(
                value = viewModel.firstName,
                onValueChange = { viewModel.firstName = it },
                label = "First Name",
                leadingIcon = Icons.Filled.Person,
                isError = viewModel.firstNameError,
            )
            LoginTextField(
                value = viewModel.lastName,
                onValueChange = { viewModel.lastName = it },
                label = "Last Name",
                isError = viewModel.lastNameError,
            )
            LoginTextField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.phoneNumber = it },
                label = "Phone Number",
                prefix = "+",
                leadingIcon = Icons.Filled.Phone,
                isError = viewModel.phoneNumberError,
            )
            LoginTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = "Email",
                leadingIcon = Icons.Filled.Email,
                supportingText = "optional",
                isError = viewModel.emailError,
            )

            Spacer(modifier = Modifier.height(0.05.dw))

            FilledIconButton(
                modifier = Modifier.width(0.20.dw),
                onClick = {
                    if (viewModel.validateFields()) {

                    }
                }
            ) {
                Text("LogIn")
            }

            if (imeState.value) {
                Spacer(modifier = Modifier.height(0.40.dh))
            }
        }
    }

}
