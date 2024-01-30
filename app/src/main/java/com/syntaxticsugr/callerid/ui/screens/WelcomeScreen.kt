package com.syntaxticsugr.callerid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    navController: NavHostController
) {
    Scaffold() {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text("Welcome")
        }
    }
}
