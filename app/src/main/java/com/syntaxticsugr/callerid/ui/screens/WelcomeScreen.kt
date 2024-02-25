package com.syntaxticsugr.callerid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.viewmodel.WelcomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeViewModel = koinViewModel(),
) {

    Scaffold() {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 0.10.dw),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                "CallerID\n------------\nCaller Identification without the headache of a complex UI and annoying ads.",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            FilledIconButton(
                modifier = Modifier
                    .width(0.20.dw)
                    .padding(top = 0.20.dw),
                onClick = {
                    viewModel.nextScreen(navController)
                }
            ) {
                Text(
                    "Next",
                )
            }
        }
    }

}
