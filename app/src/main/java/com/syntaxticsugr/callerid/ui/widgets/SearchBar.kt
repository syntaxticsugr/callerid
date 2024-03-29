package com.syntaxticsugr.callerid.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.viewmodel.SearchBarViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchBar(
    searchBarViewModel: SearchBarViewModel = koinViewModel()
) {
    var isFocused by remember { mutableStateOf(false) }

    if (searchBarViewModel.showSearchResultDialog) {
        searchBarViewModel.ShowSearchDialog()
    }

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
                value = if (isFocused) {
                    searchBarViewModel.phoneNumber
                } else {
                    "Search Phone Number"
                },
                onValueChange = { searchBarViewModel.phoneNumber = it },
                singleLine = true,
                prefix = if (isFocused) {
                    {
                        Text(
                            text = "+",
                            color = if (searchBarViewModel.phoneNumberError) {
                                MaterialTheme.colorScheme.error
                            } else {
                                Color.Unspecified
                            }
                        )
                    }
                } else {
                    null
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = if (searchBarViewModel.phoneNumberError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        Color.Unspecified
                    },
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            IconButton(
                onClick = {
                    searchBarViewModel.searchPhoneNumber()
                }
            ) {
                val size = 0.06.dw

                if (searchBarViewModel.searching) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(size),
                        strokeWidth = size / 10
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
