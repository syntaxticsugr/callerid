package com.syntaxticsugr.callerid.ui.widgets

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.utils.PhoneNumberInfoHelper
import com.syntaxticsugr.callerid.utils.isValidPhoneNumber
import com.syntaxticsugr.callerid.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SearchBar(
    context: Context,
    homeViewModel: HomeViewModel
) {
    var isFocused by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .width(0.75.dw),
        shape = RoundedCornerShape(0.25.dw)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 0.02.dw),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
                    .weight(1f),
                value = homeViewModel.searchNumber,
                onValueChange = { homeViewModel.searchNumber = it },
                singleLine = true,
                placeholder = if (!isFocused) {
                    {
                        Text(text = "Search Phone Number")
                    }
                } else {
                    null
                },
                prefix = if (isFocused) {
                    {
                        Text(
                            text = "+"
                        )
                    }
                } else {
                    null
                },
                isError = homeViewModel.searchNumberError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            IconButton(
                onClick = {
                    if (!isValidPhoneNumber(("+${homeViewModel.searchNumber.trim()}"))) {
                        homeViewModel.searchNumberError = true
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            name = PhoneNumberInfoHelper.getName(
                                context,
                                "+${homeViewModel.searchNumber.trim()}"
                            )
                        }
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        }

        if (name.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = name,
                textAlign = TextAlign.Center
            )
        }
    }
}
